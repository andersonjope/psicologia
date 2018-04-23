package br.com.jope.psicologia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.jope.psicologia.entity.MensagemSessao;
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.enumeration.EnumPerfil;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.services.MensagemSessaoService;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.MensagemSessaoVO;

@Controller
public class MensagemSessaoController extends AbstractController {

	private static final long serialVersionUID = 4901239768434290706L;
	private static Logger logger = Logger.getLogger(MensagemSessaoController.class.getName());

	@Autowired(required=true)
	@Qualifier("mensagemSessaoService")
	private MensagemSessaoService mensagemSessaoService;
	
	@RequestMapping(value="/loadMensagemSessao", method = RequestMethod.GET)
	public @ResponseBody List<MensagemSessaoVO> loadMensagemSessao(Model model, @RequestParam("nuSessao") Long nuSessao){
		List<MensagemSessaoVO> retornoList = new ArrayList<>();
		try {
			List<MensagemSessao> mensagemSessaoList = mensagemSessaoService.loadMensagemSessao(nuSessao);
			MensagemSessaoVO vo = null;
			for (MensagemSessao mensagemSessao : mensagemSessaoList) {
				Usuario usuario = mensagemSessao.getUsuario();
				vo = new MensagemSessaoVO();
				if(EnumPerfil.MEDICO.equals(usuario.getEnumPerfil())) {
					vo.setTipoUsuario("psi");
				}else {
					vo.setTipoUsuario("pac");										
				}
				vo.setDeMensagem(mensagemSessao.getDeMensagem());
				retornoList.add(vo);
			}
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
		return retornoList;
	}
	
	@RequestMapping(value="/incluirMensagemSessao", method = RequestMethod.POST)
	public @ResponseBody String incluirMensagemSessao(Model model, @RequestParam("nuSessao") Long nuSessao, @RequestParam("nuUsuario") Long nuUsuario, @RequestParam("mensagem") String mensagem) {
		try {
			if(!Util.isEmpty(mensagem)) {
				mensagemSessaoService.incluirMensagemSessao(nuSessao, nuUsuario, mensagem);				
			}
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			return "NO";
		}
		return "OK";
	}
	
}
