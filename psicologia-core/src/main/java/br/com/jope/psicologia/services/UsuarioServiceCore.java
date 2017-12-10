package br.com.jope.psicologia.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.persistence.BaseServiceCore;

@Service("usuarioService")
@Transactional
public class UsuarioServiceCore extends BaseServiceCore<Usuario> implements UsuarioService {

	private static final long serialVersionUID = -6116817864081641327L;

	@Override
	protected Class<Usuario> getBeanClass() {
		return Usuario.class;
	}

	
}
