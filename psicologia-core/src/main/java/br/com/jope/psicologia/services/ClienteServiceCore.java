package br.com.jope.psicologia.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.util.Util;
import br.com.jope.psicologia.vo.ConsultaVO;

@Service("clienteService")
@Transactional
public class ClienteServiceCore extends BaseServiceCore<Cliente> implements ClienteService {

	private static final long serialVersionUID = 4380638636957875348L;
	private static Logger logger = Logger.getLogger(ClienteServiceCore.class.getName());

	@Autowired(required=true)
	@Qualifier("usuarioService")
	private UsuarioService usuarioService;
	
	@Override
	protected Class<Cliente> getBeanClass() {
		return Cliente.class;
	}

	@Override
	public void incluir(Cliente entity) throws BussinessException {
		String encryptPassword = Util.encrypt(entity.getUsuario().getDeSenha());
		entity.getUsuario().setDeSenha(encryptPassword);
		usuarioService.incluir(entity.getUsuario());
		super.incluir(entity);
	}
	
	@Override
	public Cliente loadClientePorUsuario(Long nuUsuario) throws BussinessException {
		try {
			ConsultaVO consulta = new ConsultaVO(Cliente.FIND_CLIENTE_POR_USUARIO);
			consulta.addParametros("nuUsuario", nuUsuario);
			
			List<Cliente> list = super.loadListNamedQuery(consulta);
			
			if(!Util.isEmpty(list)) {
				return list.get(0);
			}
			
			return null;
		} catch (BussinessException e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMensagem());
		}
	}
	
}
