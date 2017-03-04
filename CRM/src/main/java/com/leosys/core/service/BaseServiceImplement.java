package com.leosys.core.service;

import com.leosys.core.cache.Cache;
import com.leosys.core.common.DatabaseType;
import com.leosys.core.dao.BaseDAO;
import com.leosys.core.common.QueryConditionEntity;
import com.leosys.core.common.QueryType;
import com.leosys.core.utils.PageCond;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Sam.Zheng <zcl1866@sina.com>
 * @date 2013-4-25 16:47:33
 * @version <v0.1>
 * @copyright SixBoy
 */
@Service("baseService")
public class BaseServiceImplement<T> implements BaseService<T> {
   
            
    @Autowired
    protected BaseDAO<T> baseDAO;
    
    public BaseDAO<T> getBaseDAO() {
        return baseDAO;
    }

    public void setBaseDAO(BaseDAO<T> baseDAO) {
        this.baseDAO = baseDAO;
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public boolean add(T entity) {
        return baseDAO.addEntity(entity);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public boolean delete(T entity) {
        return baseDAO.deleteEntity(entity);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public boolean update(T entity) {
        return baseDAO.updateEntity(entity);
    }

    @Override
    public T querySingleEntity(Class c, java.lang.Long primaryKey) {
        return baseDAO.querySingleEntity(c, primaryKey);
    }



    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public boolean removeBetchEntity(List<T> entities) {
        return baseDAO.removeBetchEntity(entities);
    }

    @Override
    @Transactional(propagation=Propagation.REQUIRED) 
    public boolean updateBatchEntity(List<T> entities) {
        return baseDAO.updateBatchEntity(entities);
    }

    /**
     * 清理内存中的受托管的bean
     */
    @Override
    public void clear() { 
        baseDAO.clear();
    }

    @Override
    public Object getCacheData(String key) { //获取缓存数据
            Cache cache = Cache.getInstance();
            if (cache != null) {
                return cache.getCacheData().get(key);
            }
        return null;
    }
    @Override
    public void setCacheData(String key, Object data) { //设置缓存数据（静态数据使用效率高)
                Cache cache = Cache.getInstance();
                Map<String, Object> caches = cache.getCacheData();
                if (caches != null) {
                    caches.put(key, data);
                }
            }

    @Override
    public List<T> queryEntities(QueryConditionEntity qce,Class<T> clazz) {
        return  baseDAO.queryEntities(qce,clazz);
    }

    @Override
    public Query getJPAQuery(QueryType type, String jpql) {
        return baseDAO.getJPAQuery(type, jpql);
    }

    @Override
    public Query getJPAQuery(QueryType type, String jpql, Class<T> clazz) {
       return baseDAO.getJPAQuery(type, jpql,clazz);
    }

    @Override
    public DatabaseType getDatabaseType() {
       return baseDAO.getDatabaseType();
    }

    @Override
    public String getWebPath() {
        return null;
    }

    @Override
    public List queryEntitiesByPage(QueryType type, String jpql, Class<T> clazz, int startRow, int pageSize) {
        return baseDAO.queryEntitiesByPage(type, jpql, clazz, startRow, pageSize);
    }

    @Override
    public int queryCount(QueryType type, String jpql) {
       return baseDAO.queryCount(type, jpql);
    }

    @Override
    public void commitTransaction() {
        baseDAO.commitTransaction();
    }

    @Override
    public void openTransaction() {
       baseDAO.openTransaction();
    }

    
    

   }
    

