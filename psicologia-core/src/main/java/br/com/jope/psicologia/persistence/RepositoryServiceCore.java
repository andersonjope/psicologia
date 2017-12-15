package br.com.jope.psicologia.persistence;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<E> loadListNamedQuery(ConsultaVO consulta) throws BussinessException {
		try {
			Query query = getEm().createNamedQuery(consulta.getQuery());
			
			setParametro(query, consulta);
			
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
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

}
