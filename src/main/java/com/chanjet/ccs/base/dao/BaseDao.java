package com.chanjet.ccs.base.dao;

import java.util.List;

public interface BaseDao <T>{
    
    public void saveT(T obj);
    
    public void updateT(T obj);
    
    public void saveTx(T obj);
    
    public void updateTx(T obj);
    
    public T getT(Class<?> clazz, Integer id);
    
    public List<T> listT(String queryName, Object... params);
    
    public int updateT(String queryName, Object... params);
    
}
