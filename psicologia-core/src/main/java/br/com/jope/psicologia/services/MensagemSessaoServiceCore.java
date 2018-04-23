package br.com.jope.psicologia.services;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.MensagemSessao;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("mensagemSessaoService")
@Transactional
public class MensagemSessaoServiceCore extends BaseServiceCore<MensagemSessao> implements MensagemSessaoService {

	private static final long serialVersionUID = 2445653755126076401L;
	private static Logger logger = Logger.getLogger(UsuarioServiceCore.class.getName());
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Autowired(required=true)
	@Qualifier("sessaoService")
	private SessaoService sessaoService;
	
	@Override
	protected Class<MensagemSessao> getBeanClass() {
		return MensagemSessao.class;
	}

	@Override
	public void incluirMensagemSessao(Long nuSessao, Long nuUsuario, String mensagem) throws BussinessException {
		try {
			Sessao sessao = sessaoService.getId(Sessao.class, nuSessao);
			Usuario usuario = usuarioService.getId(Usuario.class, nuUsuario);
			
			MensagemSessao mensagemSessao = new MensagemSessao();
			mensagemSessao.setDhInclusao(new Date());
			mensagemSessao.setUsuario(usuario);
			mensagemSessao.setSessao(sessao);
			mensagemSessao.setDeMensagem(mensagem);
			
			super.incluir(mensagemSessao);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}

	@Override
	public List<MensagemSessao> loadMensagemSessao(Long nuSessao) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(MensagemSessao.FIND_MENSAGEM_SESSAO_POR_SESSAO);
			consulta.addParametros("nuSessao", nuSessao);
			
			return loadListNamedQuery(consulta);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}

	
}
