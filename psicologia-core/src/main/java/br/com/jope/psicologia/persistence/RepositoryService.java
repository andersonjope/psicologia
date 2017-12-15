package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

public interface RepositoryService<E extends BaseEntity> extends Serializable {

	public E update(E entity) throws BussinessException;
	
	public void delete(E entity) throws BussinessException;
	
	public void persist(E entity) throws BussinessException;
	
	public List<E> getAll(Class<E> entity) throws BussinessException;
	
	public E getId(Class<E> entity, Serializable id) throws BussinessException;
	
	public List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException;
	
}
