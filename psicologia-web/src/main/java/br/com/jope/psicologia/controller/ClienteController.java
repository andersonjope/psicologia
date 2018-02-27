package br.com.jope.psicologia.controller;

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
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumPerfil;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioCliente;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.EmailService;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;

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
	public String cliente(Model model, @RequestParam("idCliente") String idCliente, HttpServletRequest request) {
		model.addAttribute("idCliente", idCliente);
		initializeWebSocket(request, idCliente);
		return "salaCliente";
	}
	
	@RequestMapping(value="/cadastrarCliente", method = RequestMethod.GET)
	public String cadastrarCliente(Model model) {
		model.addAttribute("formularioCliente", new FormularioCliente());
		loadClienteList(model);
		return "cadastrarCliente";
	}
	
	@RequestMapping(value="/salvarCliente", method = RequestMethod.POST)
	public String salvarCliente(Model model, @Valid @ModelAttribute("formularioCliente") FormularioCliente formularioCliente, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadClienteList(model);
				return "cadastrarCliente";			
			}
			
			if(usuarioService.validarUsuarioLogin(formularioCliente.getDeLogin())) {
				loadClienteList(model);
				return "cadastrarCliente";
			}
			
			Cliente cliente = new Cliente();
			Usuario usuario = new Usuario();
			cliente.setDeNome(formularioCliente.getDeNome());
			usuario.setDeLogin(formularioCliente.getDeLogin());

			if(!Util.isEmpty(formularioCliente.getCoCep())) {
				usuario.setCoCep(formularioCliente.getCoCep());
			}
			if(!Util.isEmpty(formularioCliente.getCoCPF())) {
				usuario.setCoCPF(formularioCliente.getCoCPF());
			}
			if(!Util.isEmpty(formularioCliente.getCoTelefone())) {
				usuario.setCoTelefone(formularioCliente.getCoTelefone());
			}
			if(!Util.isEmpty(formularioCliente.getDeCidade())) {
				usuario.setDeCidade(formularioCliente.getDeCidade());
			}
			if(!Util.isEmpty(formularioCliente.getDeEndereco())) {
				usuario.setDeEndereco(formularioCliente.getDeEndereco());
			}
			if(!Util.isEmpty(formularioCliente.getDeNascimento())) {
				usuario.setDtNascimento(Util.converteStringToDate(Util.FORMATO_DATA_DIA_MES_ANO, formularioCliente.getDeNascimento()));
			}
			if(!Util.isEmpty(formularioCliente.getDePais())) {
				usuario.setDePais(formularioCliente.getDePais());
			}
			if(!Util.isEmpty(formularioCliente.getDeSexo())) {
				usuario.setDeSexo(formularioCliente.getDeSexo());
			}
			
			cliente.setUsuario(usuario);
			String randomSenha = Util.getRandomSenha();
			usuario.setDeSenha(randomSenha);
			usuario.setEnumPerfil(EnumPerfil.CLIENTE);
			clienteService.incluir(cliente);
			
			emailService.enviaEmail(formularioCliente.getDeLogin(), "Cadastro de Paciente: " + cliente.getDeNome(), "Cadastro efetuado com sucesso.<br/><br/> Utilize as seguinte informações para entrar no sistema<br/> Usuário: " + formularioCliente.getDeLogin() + "<br/>Senha: " + randomSenha);
			
			loadClienteList(model);
			addMessages(model, MessageType.SUCCESS, false, "Cadastro cliente efeturado.");
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
