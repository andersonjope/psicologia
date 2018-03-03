package br.com.jope.psicologia.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.enumeration.EnumPerfil;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.services.SessaoService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.UsuarioVO;

@Controller
public class HomeController extends AbstractController {
	
	private static final long serialVersionUID = 1256433797116808827L;

	@Autowired(required=true)
	@Qualifier("sessaoService")
	private SessaoService sessaoService;
	
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		try {
			UsuarioVO usuario = loadUsuarioLogado(request);
			if(!Util.isEmpty(usuario) && !Util.isEmpty(usuario.getNuUsuario())) {
				if(EnumPerfil.MEDICO.equals(usuario.getEnumPerfil())) {
					List<Sessao> sessaoMedico = sessaoService.loadSessaoMedico(usuario.getNuUsuario());
					model.addAttribute("sessaoMedicoList", sessaoMedico);
				}else if(EnumPerfil.CLIENTE.equals(usuario.getEnumPerfil())) {
					List<Sessao> sessaoCliente = sessaoService.loadSessaoCliente(usuario.getNuUsuario());
					model.addAttribute("sessaoClienteList", sessaoCliente);
				}				
			}
		} catch (BussinessException e) {
			e.printStackTrace();
		}
		return "home";
	}

}
