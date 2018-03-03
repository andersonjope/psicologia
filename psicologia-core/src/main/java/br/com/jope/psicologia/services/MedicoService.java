package br.com.jope.psicologia.services;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface MedicoService extends BaseService<Medico> {

	Medico loadMedicoPorUsuario(Long nuUsuario) throws BussinessException;
	
}
