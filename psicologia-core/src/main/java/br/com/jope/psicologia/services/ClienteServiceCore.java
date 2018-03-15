package br.com.jope.psicologia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;
import br.com.jope.psicologia.util.Util;

@Service("clienteService")
@Transactional
public class ClienteServiceCore extends BaseServiceCore<Cliente> implements ClienteService {

	private static final long serialVersionUID = 4380638636957875348L;

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
	
}
