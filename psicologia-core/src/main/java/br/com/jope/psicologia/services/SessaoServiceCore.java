package br.com.jope.psicologia.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.Sessao;
import br.com.jope.psicologia.persistence.BaseServiceCore;

@Service("sessaoService")
@Transactional
public class SessaoServiceCore extends BaseServiceCore<Sessao> implements SessaoService {

	private static final long serialVersionUID = 3219331761214309870L;

	@Override
	protected Class<Sessao> getBeanClass() {
		return Sessao.class;
	}

	
}
