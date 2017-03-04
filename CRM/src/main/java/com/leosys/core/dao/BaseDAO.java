/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.dao;

import com.leosys.core.common.DatabaseType;
import com.leosys.core.common.QueryType;
import com.leosys.core.common.QueryConditionEntity;
import com.leosys.core.utils.PageCond;
import java.util.List;
import javax.persistence.Query;

/**
 * @author Sam.Zheng <zcl1866@sina.com>
 * @date 2013-4-25 16:30:09
 * @version <v0.1>
 * @copyright SixBoy
 */
public interface BaseDAO<T> {
	public boolean addEntity(T entity);

	public boolean deleteEntity(T entity);

	public boolean updateEntity(T entity);
        public Query getJPAQuery(QueryType type,String jpql,Class<T> clazz);
        public List  queryEntitiesByPage(QueryType type,String jpql,Class<T> clazz,int startRow,int pageSize);
	public boolean removeBetchEntity(List<T> entities);
	public boolean updateBatchEntity(List<T> objects);
	public List<T> queryEntities(QueryConditionEntity qce,Class<T> clazz);      
        public int  queryCount(QueryType type,String jpql);
        public T querySingleEntity(Class c, java.lang.Long primaryKey);
        public Query getJPAQuery(QueryType type,String jpql);
        
        public DatabaseType getDatabaseType();
        public void openTransaction();
        public void commitTransaction();
        public void clear();
}
