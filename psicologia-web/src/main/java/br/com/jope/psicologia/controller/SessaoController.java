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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.SalaSessao;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.enumeration.EnumAcaoSessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioAlteraSessao;
import br.com.jope.psicologia.model.FormularioAlteraSessaoSom;
import br.com.jope.psicologia.model.FormularioCriaSessao;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.SessaoService;
import br.com.jope.psicologia.util.Util;
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
		List<Cliente> clienteList = clienteService.getAll();
		model.addAttribute("clienteList", clienteList);
		model.addAttribute("formularioSessao", new FormularioCriaSessao());
	}
	
	@RequestMapping(value="/criarSessao", method = RequestMethod.POST)
	public String criarSessao(Model model, @Valid @ModelAttribute("formularioSessao") FormularioCriaSessao formularioSessao, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {			
			Medico medico = medicoService.loadMedicoPorUsuario(loadUsuarioLogado(request).getNuUsuario());
			
			if(sessaoService.isSessaoAberta(formularioSessao.getNuCliente(), medico)) {
				addMessages(model, MessageType.ERROR, false, "Sessão em andamento para o Paciente informado.");
				loadDados(model);
				return "iniciarSessao";
			}
			
			Cliente cliente = new Cliente();
			cliente.setNuCliente(formularioSessao.getNuCliente());
			
			Sessao sessao = new Sessao();
			sessao.setMedico(medico);
			sessao.setCliente(cliente);
			sessao.setDhInicioSessao(new Date());
			
			sessaoService.incluir(sessao);
			
			addMessages(redirectAttributes, MessageType.WARNING, false, "Sessão criada.");
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "redirect:/home";
	}
	
	//TODO REMOVER
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
			FormularioAlteraSessao formularioSessao = new FormularioAlteraSessao();
			formularioSessao.setNuSessao(sessao.getNuSessao());
			
			model.addAttribute("sessao", sessao);
			model.addAttribute("formularioSessao", formularioSessao);
			model.addAttribute("formularioSessaoSom", new FormularioAlteraSessaoSom());
			model.addAttribute("salaSessaoList", sessao.getSalaSessaoList());
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "gerenciarSessao";
	}
	
	@RequestMapping(value="/alterarSessao", method = RequestMethod.POST)
	public String alterarSessao(Model model, @Valid @ModelAttribute("formularioSessao") FormularioAlteraSessao formularioSessao, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, formularioSessao.getNuSessao());
			SalaSessao salaSessaoAnterior = new SalaSessao();
			if(!Util.isEmpty(sessao.getSalaSessaoList())) {
				salaSessaoAnterior = sessao.getSalaSessaoList().get(0);
			}
			
			SalaSessao salaSessao = new SalaSessao();
			salaSessao.setDhRegistro(new Date());
			
			boolean alteraSessao = false;
			EnumAcaoSessao acaoSessao = EnumAcaoSessao.loadCodigo(formularioSessao.getAcao());
			switch (acaoSessao) {
			case INICIAR:
				if(!Util.isEmpty(salaSessaoAnterior.getNuVelocidadeMovimento())) {
					salaSessao.setNuVelocidadeMovimento(salaSessaoAnterior.getNuVelocidadeMovimento());										
				}else {
					salaSessao.setNuVelocidadeMovimento(1);					
				}
				sessao.addSalaSessao(salaSessao);
				formularioSessao.setSessaoIniciada(true);
				alteraSessao = true;
				break;
			case PAUSAR:
				formularioSessao.setSessaoIniciada(false);
				break;
			case ENCERRAR:
				sessao.setDhFinalSessao(new Date());
				formularioSessao.setSessaoIniciada(false);
				alteraSessao = true;
				break;
			case AUMENTAR:
				if(!Util.isEmpty(salaSessaoAnterior.getNuVelocidadeMovimento())) {
					salaSessao.setNuVelocidadeMovimento(salaSessaoAnterior.getNuVelocidadeMovimento() + 1);					
					sessao.addSalaSessao(salaSessao);
				}
				alteraSessao = true;
				break;
			case DIMINUIR:
				if(!Util.isEmpty(salaSessaoAnterior.getNuVelocidadeMovimento()) && salaSessaoAnterior.getNuVelocidadeMovimento() > 1) {
					salaSessao.setNuVelocidadeMovimento(salaSessaoAnterior.getNuVelocidadeMovimento() - 1);					
					sessao.addSalaSessao(salaSessao);
				}
				alteraSessao = true;
				break;
			case SOMATIVO:
				formularioSessao.setSomAtivo(true);
				break;
			case SOMMUDO:
				formularioSessao.setSomAtivo(false);
				break;

			default:
				break;
			}
			
			if(alteraSessao) {
				sessaoService.alterar(sessao);				
			}
			
			model.addAttribute("formularioSessao", formularioSessao);
			
			notificaCliente(request, sessao.getCliente().getUsuario().getDeLogin(), salaSessao.getNuVelocidadeMovimento(), formularioSessao.isSomAtivo());
			
			if(EnumAcaoSessao.ENCERRAR.equals(acaoSessao)) {
				addMessages(redirectAttributes, MessageType.INFO, false, "Sessão encerrada, dados enviados para o Paciente.");
				return "redirect:/home";				
			}
			
			loadSessaoSalaSessao(model, sessao.getNuSessao());
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		addMessages(model, MessageType.INFO, false, "Dados enviados para o Paciente.");
		return "gerenciarSessao";
	}
	
	//TODO REMOVER
	@RequestMapping(value="/alterarSessaoSom", method = RequestMethod.POST)
	public String alterarSessaoSom(Model model, @ModelAttribute("formularioSessaoSom") FormularioAlteraSessaoSom formularioSessaoSom, BindingResult result, HttpServletRequest request) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, formularioSessaoSom.getNuSessao());
			
			formularioSessaoSom.setNuSessao(sessao.getNuSessao());
			formularioSessaoSom.setVelocidade(formularioSessaoSom.getVelocidade());
			FormularioAlteraSessao formularioSessao = new FormularioAlteraSessao();
			formularioSessao.setNuSessao(sessao.getNuSessao());
//			formularioSessao.setPlayStop(formularioSessaoSom.isPlayStop());
			
			model.addAttribute("formularioSessao", formularioSessao);
			model.addAttribute("formularioSessaoSom", formularioSessaoSom);
			model.addAttribute("sessao", sessao);
			
			notificaCliente(request, sessao.getCliente().getUsuario().getDeLogin(), formularioSessaoSom.getVelocidade(), formularioSessaoSom.isPlayStop());
			
			addMessages(model, MessageType.INFO, false, "Dados enviados para o Paciente.");
			loadSessaoSalaSessao(model, sessao.getNuSessao());
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "gerenciarSessao";
	}

	private void loadSessaoSalaSessao(Model model, Long nuSessao) throws BussinessException {
		Sessao sessaoAlterada = sessaoService.getId(Sessao.class, nuSessao);
		List<SalaSessao> salaSessaoList = sessaoAlterada.getSalaSessaoList();
		
		model.addAttribute("salaSessaoList", salaSessaoList);
	}
}
