package br.com.jope.psicologia.controller;

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

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumUsuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.model.FormularioLogin;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.vo.UsuarioVO;

@Controller
public class LoginController extends AbstractController {

	private static final long serialVersionUID = -6270307833906156938L;

	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@RequestMapping(value="/auth", method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("formularioLogin", new FormularioLogin());
		return "login";
	}
	
	@RequestMapping(value="/auth", method = RequestMethod.POST)
	public String auth(Model model, @Valid @ModelAttribute("formularioLogin") FormularioLogin formularioLogin, BindingResult result, HttpServletRequest request) {
		try {
			if(result.hasErrors()) {
				return "login";			
			}
			
			Usuario usuario = new Usuario();
			usuario.setDeLogin(formularioLogin.getEmail());
			usuario.setDeSenha(formularioLogin.getSenha());
					
			usuario = usuarioService.loadUsuarioLogin(usuario);
			
			if(Util.isEmpty(usuario)) {
				addMessages(model, MessageType.ERROR, false, "Usu�rio n�o encontrado, com login informado.");
				return "login";			
			}
			
			request.getSession(true).setAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao(), new UsuarioVO(usuario));
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "home";
	}
	
}
