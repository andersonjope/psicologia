package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

public abstract class BaseServiceCore<E extends BaseEntity> implements BaseService<E> {

	private static final long serialVersionUID = -4048759443768481563L;
	private static Logger logger = Logger.getLogger(BaseServiceCore.class.getName());
	
	@Autowired
	@Qualifier("repositoryService")
	private RepositoryService<E> repository;
	
	protected abstract Class<E> getBeanClass();
	
	@Override
	@Transactional(readOnly=false)
	public E alterar(E entity) throws BussinessException {
		try {		
			return repository.update(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void excluir(E entity) throws BussinessException {
		try {		
			repository.delete(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly=false)
	public void incluir(E entity) throws BussinessException {
		try {		
			repository.persist(entity);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new BussinessException(ex.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@Transactional(readOnly=true)
	public E getId(Class entity, Serializable id) throws BussinessException {
		try {		
			return repository.getId(entity, id);
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new BussinessException(ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<E> getAll() throws BussinessException {
		try {		
			return repository.getAll(getBeanClass());
		}catch (BussinessException be) {
			throw be;
		} catch (Exception ex) {
			logger.log(Level.SEVERE, ex.getMessage());
			throw new BussinessException(ex.getMessage());
		}
	}
	
	@Transactional(readOnly=true)
	@Override
	public List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException {
		try {
			return repository.loadListNamedQuery(consulta);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}
	
}
