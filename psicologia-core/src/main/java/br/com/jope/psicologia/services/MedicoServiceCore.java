package br.com.jope.psicologia.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("medicoService")
@Transactional
public class MedicoServiceCore extends BaseServiceCore<Medico> implements MedicoService {

	private static final long serialVersionUID = 4075679295085204488L;
	
	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Override
	protected Class<Medico> getBeanClass() {
		return Medico.class;
	}

	@Override
	public void incluir(Medico entity) throws BussinessException {
		String encryptPassword = Util.encryptPassword(entity.getUsuario().getDeSenha());
		entity.getUsuario().setDeSenha(encryptPassword);
		usuarioService.incluir(entity.getUsuario());
		super.incluir(entity);
	}

	@Override
	public Medico loadMedicoPorUsuario(Long nuUsuario) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Medico.FIND_MEDICO_POR_USUARIO);
			consulta.addParametros("nuUsuario", nuUsuario);
			
			List<Medico> list = super.loadListNamedQuery(consulta);
			
			if(!Util.isEmpty(list)) {
				return list.get(0);
			}
			
			return null;
		} catch (BussinessException e) {
			e.printStackTrace();
			throw new BussinessException(e.getMensagem());
		}
	}
	
}
