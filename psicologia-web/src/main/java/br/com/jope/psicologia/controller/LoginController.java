package br.com.jope.psicologia.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

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
import br.com.jope.psicologia.services.EmailService;
import br.com.jope.psicologia.services.UsuarioService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.view.message.MessageType;
import br.com.jope.psicologia.vo.UsuarioVO;

@Controller
public class LoginController extends AbstractController {

	private static final String LOGIN = "login";
	private static final long serialVersionUID = -6270307833906156938L;
	private static Logger logger = Logger.getLogger(LoginController.class.getName());

	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value="/auth", method = RequestMethod.GET)
	public String auth(Model model) {
		model.addAttribute("formularioLogin", new FormularioLogin());
		return LOGIN;
	}
	
	@RequestMapping(value="/sair", method = RequestMethod.GET)
	public String sair(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao());
		model.addAttribute("formularioLogin", new FormularioLogin());
		return LOGIN;
	}
	
	@RequestMapping(value="/auth", method = RequestMethod.POST)
	public String auth(Model model, @Valid @ModelAttribute("formularioLogin") FormularioLogin formularioLogin, BindingResult result, HttpServletRequest request) {
		try {
			if(formularioLogin.isRecuperaSenha()) {
				if(result.hasErrors()) {
					return LOGIN;			
				}		
				
				Usuario usuario = usuarioService.loadUsuarioLogin(formularioLogin.getEmail());
				if(Util.isEmpty(usuario)){
					addMessages(model, MessageType.ERROR, false, "Usuário não encontrado, com E-amil informado.");
					return LOGIN;								
				}
				
				String randomSenha = Util.getRandomSenha();
				usuario.setDeSenha(randomSenha);
				usuarioService.alterar(usuario);
				
				String deLogin = usuario.getDeLogin();
				emailService.enviaEmail(deLogin, "Recuperação de senha: ", "Senha recuperada com sucesso.<br/><br/> Utilize as seguinte informações para entrar no sistema<br/> Usuário: " + deLogin + "<br/>Senha: " + randomSenha);
				
			}else {
				if(result.hasErrors()) {
					return LOGIN;			
				}
				
				Usuario usuario = new Usuario();
				usuario.setDeLogin(formularioLogin.getEmail());
				usuario.setDeSenha(formularioLogin.getSenha());
				
				usuario = usuarioService.loadUsuarioLogin(usuario);
				
				if(Util.isEmpty(usuario)) {
					addMessages(model, MessageType.ERROR, false, "Usuário não encontrado, com login informado.");
					return LOGIN;			
				}
				
				request.getSession(true).setAttribute(EnumUsuario.USUARIO_LOGADO.getDescricao(), new UsuarioVO(usuario));				
			}
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return "redirect:home";
	}
	
}
