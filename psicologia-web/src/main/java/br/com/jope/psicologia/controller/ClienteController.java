package br.com.jope.psicologia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jope.psicologia.services.ClienteService;

@Controller
public class ClienteController {

	@Autowired(required=true)
	@Qualifier("clienteService")
	private ClienteService clienteService;
	
	@RequestMapping(value="/cliente", method = RequestMethod.GET)
	public String cliente(Model model) {
		return "cliente";
	}
	
}
