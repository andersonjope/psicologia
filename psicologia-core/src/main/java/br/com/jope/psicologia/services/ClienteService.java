package br.com.jope.psicologia.services;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface ClienteService extends BaseService<Cliente> {

	Cliente loadClientePorUsuario(Long nuUsuario) throws BussinessException;
	
}
