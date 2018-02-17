package br.com.jope.psicologia.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.model.FormularioLogin;
import br.com.jope.psicologia.vo.UsuarioVO;

@Controller
public class LoginController extends AbstractController {

	private static final long serialVersionUID = -6270307833906156938L;

	@RequestMapping(value="/auth", method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("formularioLogin", new FormularioLogin());
		return "login";
	}
	
	@RequestMapping(value="/auth", method = RequestMethod.POST)
	public String auth(Model model, @Valid @ModelAttribute("formularioLogin") FormularioLogin formularioLogin, BindingResult result, HttpServletRequest request) {
//		try {
			if(result.hasErrors()) {
				return "login";			
			}
			
			request.getSession(true).setAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao(), new UsuarioVO());
//		} catch (BussinessException e) {
//			e.printStackTrace();
//		}
		return "home";
	}
	
}
