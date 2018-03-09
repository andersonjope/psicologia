package br.com.jope.psicologia.services;

import br.com.jope.psicologia.entity.Usuario;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface UsuarioService extends BaseService<Usuario> {

	boolean validarUsuarioLogin(String deLogin) throws BussinessException;
	
	Usuario loadUsuarioLogin(Usuario usuario) throws BussinessException;
	
	Usuario loadUsuarioLogin(String deLogin) throws BussinessException;
}
