package br.com.jope.psicologia.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseServiceCore;

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
		usuarioService.incluir(entity.getUsuario());
		super.incluir(entity);
	}
	
}
