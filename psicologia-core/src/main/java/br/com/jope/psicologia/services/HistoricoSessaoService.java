package br.com.jope.psicologia.services;

import br.com.jope.psicologia.entity.HistoricoSessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface HistoricoSessaoService extends BaseService<HistoricoSessao> {

	void incluirHistoricoSessao(Long nuSessao, Long nuUsuario, String tipoUsuario, boolean inicio) throws BussinessException;
	
}
