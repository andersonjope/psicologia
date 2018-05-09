package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

public interface BaseService<E extends BaseEntity> extends Serializable {

	E alterar(E entity) throws BussinessException;
	
	void excluir(E entity) throws BussinessException;
	
	void incluir(E entity) throws BussinessException;
	
	@SuppressWarnings("rawtypes")
	E getId(Class entity,Serializable id) throws BussinessException;

	List<E> getAll()  throws BussinessException;
	
	List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException;
	
	void deleteAll(Class<E> entity, String where) throws BussinessException;
	
}
