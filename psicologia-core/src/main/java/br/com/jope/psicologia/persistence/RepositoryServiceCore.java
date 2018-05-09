package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.jope.psicologia.entity.BaseEntity;
import br.com.jope.psicologia.exception.BussinessException;
import br.com.jope.psicologia.vo.ConsultaVO;

@Repository("repositoryService")
@Transactional
public class RepositoryServiceCore<E extends BaseEntity> implements RepositoryService<E> {

	private static final long serialVersionUID = 1489170320973529521L;
	private static Logger logger = Logger.getLogger(RepositoryServiceCore.class.getName());
	
	@PersistenceContext
	private transient EntityManager em;
	

	public EntityManager getEm() {
		return em;
	}

	@Override
	public E update(E entity) throws BussinessException {
		try {
			return getEm().merge(entity);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	@Override
	public void delete(E entity) throws BussinessException {
		try {
			getEm().remove(entity);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	@Override
	public void persist(E entity) throws BussinessException {
		try {
			getEm().persist(entity);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getAll(Class<E> entity) throws BussinessException {
		try {
			return getEm().createQuery(" from " + entity.getSimpleName()).getResultList();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	@Override
	public E getId(Class<E> entity, Serializable id) throws BussinessException {
		try {
			return getEm().find(entity, id);
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException {
		try {
			Query query = getEm().createNamedQuery(consulta.getQuery());
			
			setParametro(query, consulta);
			
			return query.getResultList();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

	private void setParametro(Query query, ConsultaVO consulta) {
		if(!consulta.getParametros().isEmpty()) {
			for(Map.Entry<String, Object> parametros : consulta.getParametros().entrySet()) {
				String chave = parametros.getKey();
				Object valor = parametros.getValue();
				if(valor instanceof Integer[]){		
					List<Integer> valors = Arrays.asList((Integer[]) valor);
					query.setParameter(chave, valors);												
				}else if(valor instanceof Long[]){		
					List<Long> valors = Arrays.asList((Long[]) valor);
					query.setParameter(chave, valors);												
				}else if(valor instanceof String){
					query.setParameter(chave, String.valueOf(valor));																	
				}else{
					query.setParameter(chave, valor);												
				}
			}
		}
	}

	@Override
	public void deleteAll(Class<E> entity, String where) throws BussinessException {
		try {
			getEm().createQuery(" delete from " + entity.getSimpleName() + " where " + where).executeUpdate();
		} catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new BussinessException(e.getMessage());
		}
	}

}
