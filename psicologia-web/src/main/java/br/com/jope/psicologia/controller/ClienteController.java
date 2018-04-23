package br.com.jope.psicologia.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.enumeration.EnumPerfil;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioCliente;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.EmailService;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.vo.UsuarioVO;

@Controller
public class ClienteController extends AbstractController {

	private static final String CADASTRAR_CLIENTE = "cadastrarCliente";
	private static final long serialVersionUID = -3883844098757797700L;
	private static Logger logger = Logger.getLogger(ClienteController.class.getName());

	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/cliente", method = RequestMethod.GET)
	public String cliente(Model model, HttpServletRequest request, @RequestParam("nuSessao") Long nuSessao) {
		UsuarioVO usuario = loadUsuarioLogado(request);
		String hashSessao = Util.encrypt(String.valueOf(usuario.getNuUsuario()));
		model.addAttribute("hashSessao", hashSessao);
		model.addAttribute("nuSessao", nuSessao);
		model.addAttribute("nuUsuario", usuario.getNuUsuario());
		return "salaCliente";
	}
	
	@RequestMapping(value="/cadastrarCliente", method = RequestMethod.GET)
	public String cadastrarCliente(Model model) {
		model.addAttribute("formularioCliente", new FormularioCliente());
		loadClienteList(model);
		return CADASTRAR_CLIENTE;
	}
	
	@RequestMapping(value="/salvarCliente", method = RequestMethod.POST)
	public String salvarCliente(Model model, @Validated @ModelAttribute("formularioCliente") FormularioCliente formularioCliente, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadClienteList(model);
				return CADASTRAR_CLIENTE;			
			}
			
			if(usuarioService.validarUsuarioLogin(formularioCliente.getCliente().getUsuario().getDeLogin())) {
				addMessages(model, MessageType.WARNING, false, "E-mail já informado para outro Usuário.");
				loadClienteList(model);
				return CADASTRAR_CLIENTE;
			}
			
			Cliente cliente = formularioCliente.getCliente();
			removeCaracter(cliente.getUsuario());
			
			if(!Util.isEmpty(formularioCliente.getDeNascimento())) {
				cliente.getUsuario().setDtNascimento(Util.converteStringToDate(Util.FORMATO_DATA_DIA_MES_ANO, formularioCliente.getDeNascimento()));
			}

			String randomSenha = Util.getRandomSenha();
			cliente.getUsuario().setDeSenha(randomSenha);
			cliente.getUsuario().setEnumPerfil(EnumPerfil.CLIENTE);
			clienteService.incluir(cliente);
			
			String deLogin = cliente.getUsuario().getDeLogin();
			emailService.enviaEmail(deLogin, "Cadastro de Paciente: " + cliente.getUsuario().getDeNome(), "Cadastro efetuado com sucesso.<br/><br/> Utilize as seguinte informações para entrar no sistema<br/> Usuário: " + deLogin + "<br/>Senha: " + randomSenha);
			
			loadClienteList(model);
			addMessages(model, MessageType.SUCCESS, false, "Cadastro cliente efetuado.");
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		
		return CADASTRAR_CLIENTE;
	}

	private void loadClienteList(Model model) {
		try {
			List<Cliente> clienteList = clienteService.getAll();
			model.addAttribute("clienteList", clienteList);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
}
