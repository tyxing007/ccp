package com.chanjet.ccs.base.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

@Transactional 
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
    
    //private static final String dbUnitName = Configure.getString("service.name");
    

    //@PersistenceContext(unitName="ccsMysql")
    @PersistenceContext
    public EntityManager em;
    
    public void saveT(T obj) {
        em.persist(obj);
    }
    
    public void updateT(T obj) {
        em.merge(obj);
    }
    
    public void saveTx(T obj) {
        em.persist(obj);
    }
    
    public void updateTx(T obj) {
        em.merge(obj);
    }
    
    @SuppressWarnings("unchecked")
	public T getT(Class<?> clazz, Integer id) {
        return (T)em.find(clazz, id);
    }
    
    @SuppressWarnings("unchecked")
	public List<T> listT(String queryName, Object... params) {
        Query query = em.createNamedQuery(queryName);
        for (int pos = 1; pos <= params.length; pos++)
            query.setParameter(pos, params[pos-1]);
        return query.getResultList();
    }
    
    public int updateT(String queryName, Object... params) {
        Query query = em.createNamedQuery(queryName);
        for (int pos = 1; pos <= params.length; pos++)
            query.setParameter(pos, params[pos-1]);
        return query.executeUpdate();
    }
    
}
