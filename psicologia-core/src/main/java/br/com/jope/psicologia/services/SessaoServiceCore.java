package br.com.jope.psicologia.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("sessaoService")
@Transactional
public class SessaoServiceCore extends BaseServiceCore<Sessao> implements SessaoService {

	private static final long serialVersionUID = 3219331761214309870L;
	private static Logger logger = Logger.getLogger(SessaoServiceCore.class.getName());

	@Override
	protected Class<Sessao> getBeanClass() {
		return Sessao.class;
	}

	@Override
	public List<Sessao> loadSessaoMedico(Long nuUsuario) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Sessao.FIND_SESSAO_MEDICO);
			consulta.addParametros("nuUsuario", nuUsuario);

			return super.loadListNamedQuery(consulta);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}

	@Override
	public List<Sessao> loadSessaoCliente(Long nuUsuario) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Sessao.FIND_SESSAO_CLIENTE);
			consulta.addParametros("nuUsuario", nuUsuario);

			return super.loadListNamedQuery(consulta);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}

	@Override
	public boolean isSessaoAberta(Long nuCliente, Medico medico) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Sessao.FIND_SESSAO_ABERTA_CLIENTE_MEDICO);
			consulta.addParametros("nuCliente", nuCliente);
			consulta.addParametros("nuMedico", medico.getNuMedico());
			List<Sessao> list = super.loadListNamedQuery(consulta); 
			
			return !Util.isEmpty(list);
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}

	
}
