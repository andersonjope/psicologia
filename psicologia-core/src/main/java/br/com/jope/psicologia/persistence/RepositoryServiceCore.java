package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;

public class RepositoryServiceCore<E extends BaseEntity> implements RepositoryService<E> {

	private static final long serialVersionUID = 1489170320973529521L;
	
	@PersistenceContext
	private EntityManager em;
	

	public EntityManager getEm() {
		return em;
	}

	@Override
	public E update(E entity) throws BussinessException {
		try {
			E merge = getEm().merge(entity);
			return merge;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void delete(E entity) throws BussinessException {
		try {
			getEm().remove(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public void persist(E entity) throws BussinessException {
		try {
			getEm().persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll(Class<E> entity) throws BussinessException {
		try {
			List<E> resultList = getEm().createQuery(" from " + entity.getSimpleName()).getResultList();
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public E getId(Class<E> entity, Serializable id) throws BussinessException {
		try {
			E find = getEm().find(entity, id);
			return find;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

}
