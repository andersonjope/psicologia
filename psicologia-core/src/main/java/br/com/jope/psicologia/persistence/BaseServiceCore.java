package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;

public abstract class BaseServiceCore<E extends BaseEntity> implements BaseService<E> {

	private static final long serialVersionUID = -4048759443768481563L;
	
	@Autowired
	private RepositoryService<E> repository;
	
	protected abstract Class<E> getBeanClass();
	
	@Override
	public E alterar(E entity) throws BussinessException {
		try {		
			return repository.update(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	public void excluir(E entity) throws BussinessException {
		try {		
			repository.delete(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	public void incluir(E entity) throws BussinessException {
		try {		
			repository.persist(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BussinessException(ex.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public E getId(Class entity, Serializable id) throws BussinessException {
		try {		
			return repository.getId(entity, id);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	public List<E> getAll() throws BussinessException {
		try {		
			return repository.getAll(getBeanClass());
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BussinessException(ex.getMessage());
		}
	}

}
