package br.com.jope.psicologia.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.SalaSessao;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioAlteraSessao;
import br.com.jope.psicologia.model.FormularioCriaSessao;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.SessaoService;
import br.com.jope.psicologia.view.message.MessageType;

@Controller
public class SessaoController extends AbstractController {

	private static final long serialVersionUID = -5671612353886861039L;

	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired(required=true)
	@Qualifier("sessaoService")
	private SessaoService sessaoService;
	
	@RequestMapping(value="/iniciarSessao", method = RequestMethod.GET)
	public String iniciarSessao(Model model) {
		try {
			loadDados(model);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "iniciarSessao";
	}

	private void loadDados(Model model) throws BussinessException {
		List<Medico> medicoList = medicoService.getAll();
		List<Cliente> clienteList = clienteService.getAll();
		List<Sessao> sessaoList = sessaoService.getAll();
		model.addAttribute("medicoList", medicoList);
		model.addAttribute("clienteList", clienteList);
		model.addAttribute("sessaoList", sessaoList);
		model.addAttribute("formularioSessao", new FormularioCriaSessao());
	}
	
	@RequestMapping(value="/criarSessao", method = RequestMethod.POST)
	public String criarSessao(Model model, @Valid @ModelAttribute("formularioSessao") FormularioCriaSessao formularioSessao, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadDados(model);
				return "iniciarSessao";
			}
			
			Cliente cliente = new Cliente();
			cliente.setNuCliente(formularioSessao.getNuCliente());
			
			Medico medico = new Medico();
			medico.setNuMedico(formularioSessao.getNuMedico());
			
			Sessao sessao = new Sessao();
			sessao.setMedico(medico);
			sessao.setCliente(cliente);
			sessao.setDhInicioSessao(new Date());
			
			sessaoService.incluir(sessao);
			
			loadDados(model);
			addMessages(model, MessageType.WARNING, false, "Sessão criada.");
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "iniciarSessao";
	}
	
	@RequestMapping(value="/encerrarSessao", method = RequestMethod.GET)
	public String encerrarSessao(Model model, @RequestParam("sessao") Long nuSessao) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, nuSessao);
			sessao.setDhFinalSessao(new Date());
			
			sessaoService.alterar(sessao);
			
			loadDados(model);
			addMessages(model, MessageType.SUCCESS, false, "Sessão encerrada.");
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "iniciarSessao";
	}
	
	@RequestMapping(value="/gerenciarSessao", method = RequestMethod.GET)
	public String gerenciarSessao(Model model, @RequestParam("sessao") Long nuSessao) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, nuSessao);
			model.addAttribute("sessao", sessao);
			FormularioAlteraSessao formularioSessao = new FormularioAlteraSessao();
			formularioSessao.setNuSessao(sessao.getNuSessao());
			model.addAttribute("formularioSessao", formularioSessao);
			model.addAttribute("salaSessaoList", sessao.getSalaSessaoList());
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "gerenciarSessao";
	}
	
	@RequestMapping(value="/alterarSessao", method = RequestMethod.POST)
	public String alterarSessao(Model model, @Valid @ModelAttribute("formularioSessao") FormularioAlteraSessao formularioSessao, BindingResult result, HttpServletRequest request) {
		try {
			if(result.hasErrors()) {
				loadSessaoSalaSessao(model, formularioSessao);
				return "gerenciarSessao";
			}
			
			Sessao sessao = sessaoService.getId(Sessao.class, formularioSessao.getNuSessao());
			model.addAttribute("sessao", sessao);
			
			SalaSessao salaSessao = new SalaSessao();
			salaSessao.setDhRegistro(new Date());
			salaSessao.setNuVelocidadeMovimento(formularioSessao.getVelocidade());
			if(formularioSessao.getAltura() != null) {
				salaSessao.setNuAltura(formularioSessao.getAltura());
			}
			if(formularioSessao.getLargura() != null) {
				salaSessao.setNuLargura(formularioSessao.getLargura());
			}
			
			sessao.addSalaSessao(salaSessao);
			
			sessaoService.alterar(sessao);
			
			notificaCliente(request, sessao.getCliente().getUsuario().getDeLogin(), salaSessao.getNuVelocidadeMovimento(), formularioSessao.getAltura(), formularioSessao.getLargura());
			
			loadSessaoSalaSessao(model, formularioSessao);
			addMessages(model, MessageType.INFO, false, "Dados enviados para o cliente.");
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "gerenciarSessao";
	}

	private void loadSessaoSalaSessao(Model model, FormularioAlteraSessao formularioSessao) throws BussinessException {
		Sessao sessaoAlterada = sessaoService.getId(Sessao.class, formularioSessao.getNuSessao());
		List<SalaSessao> salaSessaoList = sessaoAlterada.getSalaSessaoList();
		
		model.addAttribute("salaSessaoList", salaSessaoList);
	}
}
