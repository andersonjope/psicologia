package br.com.jope.psicologia.controller;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import br.com.jope.psicologia.model.FormularioCriaSessao;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.SessaoService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;

@Controller
public class SessaoController extends AbstractController {

	private static final String FORMULARIO_SESSAO = "formularioSessao";
	private static final String INICIAR_SESSAO = "iniciarSessao";
	private static final long serialVersionUID = -5671612353886861039L;
	private static Logger logger = Logger.getLogger(SessaoController.class.getName());

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
			logger.log(Level.SEVERE, e.getMessage());
		}
		return INICIAR_SESSAO;
	}

	private void loadDados(Model model) throws BussinessException {
		List<Cliente> clienteList = clienteService.getAll();
		model.addAttribute("clienteList", clienteList);
		model.addAttribute(FORMULARIO_SESSAO, new FormularioCriaSessao());
	}
	
	@RequestMapping(value="/criarSessao", method = RequestMethod.POST)
	public String criarSessao(Model model, @Valid @ModelAttribute(FORMULARIO_SESSAO) FormularioCriaSessao formularioSessao, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {			
			Medico medico = medicoService.loadMedicoPorUsuario(loadUsuarioLogado(request).getNuUsuario());
			
			if(sessaoService.isSessaoAberta(medico)) {
				addMessages(model, MessageType.ERROR, false, "Sessão em andamento para o Paciente informado.");
				loadDados(model);
				return INICIAR_SESSAO;
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
			logger.log(Level.SEVERE, e.getMessage());
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value="/gerenciarSessao", method = RequestMethod.GET)
	public String gerenciarSessao(Model model, @RequestParam("sessao") Long nuSessao, HttpServletRequest request) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, nuSessao);
			FormularioAlteraSessao formularioSessao = new FormularioAlteraSessao();
			loadDadoUltimaMovimentacao(sessao, formularioSessao);
			formularioSessao.setNuSessao(sessao.getNuSessao());
			String hashSessao = Util.encrypt(String.valueOf(sessao.getCliente().getUsuario().getNuUsuario()));
			model.addAttribute("sessao", sessao);
			model.addAttribute(FORMULARIO_SESSAO, formularioSessao);
			model.addAttribute("hashSessao", hashSessao);
			initializeWebSocket(request, hashSessao);
			initializeWebSocketWebRTC(request, hashSessao);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return "gerenciarSessao";
	}

	private void loadDadoUltimaMovimentacao(Sessao sessao, FormularioAlteraSessao formularioSessao) {
		SalaSessao salaSessaoAnterior = new SalaSessao();
		if(!Util.isEmpty(sessao.getSalaSessaoList())) {
			salaSessaoAnterior = sessao.getSalaSessaoList().get(0);
		}
		if(!Util.isEmpty(salaSessaoAnterior.getNuVelocidadeMovimento())) {
			formularioSessao.setNuVelocidadeMovimento(salaSessaoAnterior.getNuVelocidadeMovimento());										
		}else {
			formularioSessao.setNuVelocidadeMovimento(1);										
		}
	}
	
	@RequestMapping(value="/alterarSessao", method = RequestMethod.POST)
	public String alterarSessao(Model model, @Valid @ModelAttribute(FORMULARIO_SESSAO) FormularioAlteraSessao formularioSessao, BindingResult result, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, formularioSessao.getNuSessao());
			SalaSessao salaSessao = new SalaSessao();
			salaSessao.setDhRegistro(new Date());
			
			boolean alteraSessao = false;
			EnumAcaoSessao acaoSessao = EnumAcaoSessao.loadCodigo(formularioSessao.getAcao());
			switch (acaoSessao) {
			case INICIAR:
				salaSessao.setNuVelocidadeMovimento(formularioSessao.getNuVelocidadeMovimento());										
				sessao.addSalaSessao(salaSessao);
				formularioSessao.setSessaoIniciada(true);
				alteraSessao = true;
				break;
			case PAUSAR:
				formularioSessao.setSessaoIniciada(false);
				break;
			case ENCERRAR:
				sessao.setDhFinalSessao(new Date());
				alteraSessao = true;
				break;
			case AUMENTAR:
				salaSessao.setNuVelocidadeMovimento(formularioSessao.getNuVelocidadeMovimento());
				alteraSessao = true;
				break;
			case DIMINUIR:
				if(!Util.isEmpty(formularioSessao.getNuVelocidadeMovimento()) && formularioSessao.getNuVelocidadeMovimento() > 1) {
					salaSessao.setNuVelocidadeMovimento(formularioSessao.getNuVelocidadeMovimento());				
					sessao.addSalaSessao(salaSessao);
				}
				alteraSessao = true;
				break;
			case SOM_ATIVO:
				formularioSessao.setSomAtivo(true);
				salaSessao.setNuVelocidadeMovimento(formularioSessao.getNuVelocidadeMovimento());
				break;
			case SOM_MUDO:
				formularioSessao.setSomAtivo(false);
				salaSessao.setNuVelocidadeMovimento(formularioSessao.getNuVelocidadeMovimento());
				break;

			default:
				break;
			}
			
			if(alteraSessao) {
				sessaoService.alterar(sessao);				
			}
			
			String hashSessao = Util.encrypt(String.valueOf(sessao.getCliente().getUsuario().getNuUsuario()));
			model.addAttribute("hashSessao", hashSessao);
			model.addAttribute(FORMULARIO_SESSAO, formularioSessao);
			
			if(EnumAcaoSessao.ENCERRAR.equals(acaoSessao)) {
				addMessages(redirectAttributes, MessageType.INFO, false, "Sessão encerrada, dados enviados para o Paciente.");
				return "redirect:/home";				
			}
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return "sessao/controlesSessao";
	}
	
}
