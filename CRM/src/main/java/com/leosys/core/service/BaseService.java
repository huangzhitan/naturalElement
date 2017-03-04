package com.leosys.core.service;

import com.leosys.core.cache.Cache;
import com.leosys.core.common.DatabaseType;
import com.leosys.core.common.QueryConditionEntity;
import com.leosys.core.common.QueryType;
import com.leosys.core.utils.PageCond;
import java.util.List;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Sam.Zheng <zcl1866@sina.com>
 * @date 2013-4-25 16:30:09
 * @version <v0.1>
 * @copyright SixBoy
 */
public interface BaseService<T>{
    
	public boolean add(T entity);
	public boolean delete(T entity);
	public boolean update(T entity);
	public T querySingleEntity(Class c, java.lang.Long primaryKey);
	public boolean removeBetchEntity(List<T> entities);
	public boolean updateBatchEntity(List<T> entities);
	public List<T> queryEntities(QueryConditionEntity qce,Class<T> clazz);
        public List queryEntitiesByPage(QueryType type, String jpql, Class<T> clazz,int startRow,int pageSize);
        public void clear();
        public Object getCacheData(String key);
        public void setCacheData(String key,Object data);
        public Query getJPAQuery(QueryType type,String jpql);
        public Query getJPAQuery(QueryType type,String jpql,Class<T> clazz);
        public String getWebPath();
        public int queryCount(QueryType type, String jpql);
        public DatabaseType getDatabaseType();
        public void commitTransaction();
        public void openTransaction();
}
