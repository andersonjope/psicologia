package br.com.jope.psicologia.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.persistence.BaseServiceCore;

@Service("medicoService")
@Transactional
public class MedicoServiceCore extends BaseServiceCore<Medico> implements MedicoService {

	private static final long serialVersionUID = 4075679295085204488L;

	@Override
	protected Class<Medico> getBeanClass() {
		return Medico.class;
	}

	
}
