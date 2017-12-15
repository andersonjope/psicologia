package br.com.jope.psicologia.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioCliente;
import br.com.jope.psicologia.services.ClienteService;
import br.com.jope.psicologia.services.UsuarioService;

@Controller
public class ClienteController {

	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/cliente", method = RequestMethod.GET)
	public String cliente(Model model) {
		return "cliente";
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
			
			cliente.setUsuario(usuario);
		
			clienteService.incluir(cliente);
			
			loadClienteList(model);
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
