package br.com.jope.psicologia.services;

import java.util.List;

import br.com.jope.psicologia.entity.Medico;
import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface SessaoService extends BaseService<Sessao> {

	List<Sessao> loadSessaoMedico(Long nuUsuario) throws BussinessException;
	
	List<Sessao> loadSessaoCliente(Long nuUsuario) throws BussinessException;

	boolean isSessaoAberta(Medico medico) throws BussinessException;
	
}
