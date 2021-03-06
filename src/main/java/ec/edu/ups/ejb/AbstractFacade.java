package ec.edu.ups.ejb;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

public abstract class AbstractFacade<T> {
	
	private Class<T> entityClass;
	
	public AbstractFacade(Class<T> entityClass) {
		this.entityClass=entityClass;
	}
	protected abstract EntityManager getEntityManager();
	
	public void create(T entity){
		getEntityManager().persist(entity);
	}
	public void edit(T entity)throws SQLException{
		getEntityManager().merge(entity);
	}
	public void remove(T entity)throws SQLException{
		getEntityManager().remove(getEntityManager().merge(entity));
	}
	public T find(Object id) throws SQLException {
		return getEntityManager().find(entityClass, id);
	}
	public List<T> findAll()throws SQLException{
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		cq.select(cq.from(entityClass));
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	public int count()throws SQLException {
		javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		javax.persistence.Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
