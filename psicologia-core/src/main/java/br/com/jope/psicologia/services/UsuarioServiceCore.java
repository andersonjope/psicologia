package br.com.jope.psicologia.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("usuarioService")
@Transactional
public class UsuarioServiceCore extends BaseServiceCore<Usuario> implements UsuarioService {

	private static final long serialVersionUID = -6116817864081641327L;

	@Override
	protected Class<Usuario> getBeanClass() {
		return Usuario.class;
	}

	@Override
	public boolean validarUsuarioLogin(String deLogin) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Usuario.FIND_USUARIO_LOGIN);
			consulta.addParametros("deLogin", deLogin);
			List<Usuario> list = loadListNamedQuery(consulta);
			if(!list.isEmpty()) {
				return true;				
			}
			return false;
		} catch (BussinessException e) {
			e.printStackTrace();
			throw new BussinessException(e.getMensagem());
		}
	}
	
}
