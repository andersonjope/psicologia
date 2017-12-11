package br.com.jope.psicologia.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Cliente;
import br.com.jope.psicologia.persistence.BaseServiceCore;

@Service("clienteService")
@Transactional
public class ClienteServiceCore extends BaseServiceCore<Cliente> implements ClienteService {

	private static final long serialVersionUID = 4380638636957875348L;

	@Override
	protected Class<Cliente> getBeanClass() {
		return Cliente.class;
	}

	
}
