package br.com.jope.psicologia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.enumeration.EnumPerfil;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioMedico;
import br.com.jope.psicologia.services.EmailService;
import br.com.jope.psicologia.services.MedicoService;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;

@Controller
public class MedicoController extends AbstractController {

	private static final long serialVersionUID = 5433459120147438409L;

	@Autowired(required=true)
	@Qualifier("medicoService")
	private MedicoService medicoService;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
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
	public String salvarMedico(Model model, @Validated @ModelAttribute("formularioMedico") FormularioMedico formularioMedico, BindingResult result) {
		try {
			if(result.hasErrors()) {
				loadMedicoList(model);
				return "cadastrarMedico";			
			}
			
			if(usuarioService.validarUsuarioLogin(formularioMedico.getMedico().getUsuario().getDeLogin())) {
				addMessages(model, MessageType.WARNING, false, "E-mail já informado para outro Usuário.");
				loadMedicoList(model);
				return "cadastrarMedico";
			}
			
			Medico medico = formularioMedico.getMedico();
			removeCaracter(medico.getUsuario());
			
			if(!Util.isEmpty(formularioMedico.getDeNascimento())) {
				medico.getUsuario().setDtNascimento(Util.converteStringToDate(Util.FORMATO_DATA_DIA_MES_ANO, formularioMedico.getDeNascimento()));
			}
			
			String randomSenha = Util.getRandomSenha();
			medico.getUsuario().setDeSenha(randomSenha);
			medico.getUsuario().setEnumPerfil(EnumPerfil.MEDICO);
			medicoService.incluir(medico);
			
			String deLogin = medico.getUsuario().getDeLogin();
			emailService.enviaEmail(deLogin, "Cadastro de Psicólogo: " + medico.getUsuario().getDeNome(), "Cadastro efetuado com sucesso.<br/><br/> Utilize as seguinte informações para entrar no sistema<br/> Usuário: " + deLogin + "<br/>Senha: " + randomSenha);
			
			loadMedicoList(model);
			addMessages(model, MessageType.SUCCESS, false, "Cadastro médico efeturado.");
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
