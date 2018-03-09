package br.com.jope.psicologia.controller;

import java.util.List;

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

	private static final long serialVersionUID = -3883844098757797700L;

	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/cliente", method = RequestMethod.GET)
	public String cliente(Model model, HttpServletRequest request) {
		UsuarioVO usuario = loadUsuarioLogado(request);
		model.addAttribute("idCliente", usuario.getDeEmail());
		initializeWebSocket(request, usuario.getDeEmail());
		return "salaCliente";
	}
	
	@RequestMapping(value="/cadastrarCliente", method = RequestMethod.GET)
	public String cadastrarCliente(Model model) {
		model.addAttribute("formularioCliente", new FormularioCliente());
		loadClienteList(model);
		return "cadastrarCliente";
	}
	
	@RequestMapping(value="/salvarCliente", method = RequestMethod.POST)
	public String salvarCliente(Model model, @Validated @ModelAttribute("formularioCliente") FormularioCliente formularioCliente, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadClienteList(model);
				return "cadastrarCliente";			
			}
			
			if(usuarioService.validarUsuarioLogin(formularioCliente.getCliente().getUsuario().getDeLogin())) {
				addMessages(model, MessageType.WARNING, false, "E-mail já informado para outro Usuário.");
				loadClienteList(model);
				return "cadastrarCliente";
			}
			
			Cliente cliente = formularioCliente.getCliente();

			if(!Util.isEmpty(formularioCliente.getDeNascimento())) {
				cliente.getUsuario().setDtNascimento(Util.converteStringToDate(Util.FORMATO_DATA_DIA_MES_ANO, formularioCliente.getDeNascimento()));
			}

			String randomSenha = Util.getRandomSenha();
			cliente.getUsuario().setDeSenha(randomSenha);
			cliente.getUsuario().setEnumPerfil(EnumPerfil.CLIENTE);
			clienteService.incluir(cliente);
			
			String deLogin = cliente.getUsuario().getDeLogin();
			emailService.enviaEmail(deLogin, "Cadastro de Paciente: " + cliente.getDeNome(), "Cadastro efetuado com sucesso.<br/><br/> Utilize as seguinte informações para entrar no sistema<br/> Usuário: " + deLogin + "<br/>Senha: " + randomSenha);
			
			loadClienteList(model);
			addMessages(model, MessageType.SUCCESS, false, "Cadastro cliente efetuado.");
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		
		return "cadastrarCliente";
	}

	private void loadClienteList(Model model) {
		try {
			List<Cliente> clienteList = clienteService.getAll();
			model.addAttribute("clienteList", clienteList);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
	}
	
}
