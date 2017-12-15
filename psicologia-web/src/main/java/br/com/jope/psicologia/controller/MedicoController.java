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
import org.springframework.web.servlet.ModelAndView;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioMedico;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.UsuarioService;

@Controller
public class MedicoController {

	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/medico", method = RequestMethod.GET)
	public ModelAndView medico(Model model) {
		return new ModelAndView("medico");
	}
	
	@RequestMapping(value="/cadastrarMedico", method = RequestMethod.GET)
	public String cadastrarMedico(Model model) {
		model.addAttribute("formularioMedico", new FormularioMedico());
		loadMedicoList(model);
		return "cadastrarMedico";
	}
	
	@RequestMapping(value="/salvarMedico", method = RequestMethod.POST)
	public String salvarMedico(Model model, @Valid @ModelAttribute("formularioMedico") FormularioMedico formularioMedico, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadMedicoList(model);
				return "cadastrarMedico";			
			}
			
			Medico medico = new Medico();
			Usuario usuario = new Usuario();
			medico.setDeNome(formularioMedico.getDeNome());
			usuario.setDeLogin(formularioMedico.getDeLogin());
			
			medico.setUsuario(usuario);
		
			medicoService.incluir(medico);
			
			loadMedicoList(model);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		
		return "cadastrarMedico";
	}

	private void loadMedicoList(Model model) {
		try {
			List<Medico> medicoList = medicoService.getAll();
			model.addAttribute("medicoList", medicoList);
		} catch (BussinessException e) {
			e.printStackTrace();
		}
	}
	
}
