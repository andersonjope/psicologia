package br.com.jope.psicologia.services;

import java.util.List;

import br.com.jope.psicologia.entity.MensagemSessao;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.persistence.BaseService;

public interface MensagemSessaoService extends BaseService<MensagemSessao> {

	void incluirMensagemSessao(Long nuSessao, Long nuUsuario, String mensagem) throws BussinessException;

	List<MensagemSessao> loadMensagemSessao(Long nuSessao) throws BussinessException;
	
}
