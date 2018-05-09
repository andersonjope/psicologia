package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

interface RepositoryService<E extends BaseEntity> extends Serializable {

	E update(E entity) throws BussinessException;
	
	void delete(E entity) throws BussinessException;
	
	void persist(E entity) throws BussinessException;
	
	List<E> getAll(Class<E> entity) throws BussinessException;
	
	E getId(Class<E> entity, Serializable id) throws BussinessException;
	
	List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException;
	
	void deleteAll(Class<E> entity, String where) throws BussinessException;
	
}
