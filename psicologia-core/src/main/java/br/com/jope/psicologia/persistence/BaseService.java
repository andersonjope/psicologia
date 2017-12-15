package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

public interface BaseService<E extends BaseEntity> extends Serializable {

	public E alterar(E entity) throws BussinessException;
	
	public void excluir(E entity) throws BussinessException;
	
	public void incluir(E entity) throws BussinessException;
	
	@SuppressWarnings("rawtypes")
	public E getId(Class entity,Serializable id) throws BussinessException;

	public List<E> getAll()  throws BussinessException;
	
	public List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException;
	
}
