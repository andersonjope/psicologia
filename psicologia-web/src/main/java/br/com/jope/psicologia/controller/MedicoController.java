package br.com.jope.psicologia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.jope.psicologia.services.MedicoService;

@Controller
public class MedicoController {

	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@RequestMapping(value="/medico", method = RequestMethod.GET)
	public ModelAndView medico(Model model) {
		return new ModelAndView("medico");
	}
	
}
