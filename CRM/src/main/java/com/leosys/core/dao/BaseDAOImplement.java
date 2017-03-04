/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.dao;

import com.leosys.core.common.QueryConditionEntity;
import com.leosys.core.common.QueryType;
import com.leosys.core.common.DataType;
import com.leosys.core.common.DatabaseType;
import com.leosys.core.utils.PageCond;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
/**
 * @author Sam.Zheng <zcl1866@sina.com>
 * @param <T>
 * @date 2013-4-25 16:30:09
 * @version <v0.1>
 */
@Repository("baseDAO")
public class BaseDAOImplement<T> implements BaseDAO<T> {

    static Logger logger = Logger.getLogger(BaseDAOImplement.class);
    @PersistenceContext
    private EntityManager entityManager;
    /*
     * 
     * @param object
     * 
     * @return
     * 
     */
    @Override
    public boolean addEntity(T object) {
        try {
            entityManager.persist(object);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
   @Override 
    public void commitTransaction(){
         entityManager.getTransaction().commit();
    }

    /**
     * 批量插入数据
     *
     * @param objects
     * @return
     */
    public boolean addBatchEntity(List<T> objects) {
        try {

            Iterator iter = objects.iterator();
            int i = 0;
            while (iter.hasNext()) {
                T obj = (T) iter.next();
                entityManager.persist(obj);
                i++;
                if (i > 100) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            // logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 批量更新数据
     *
     * @param objects
     * @return
     */
    @Override
    public boolean updateBatchEntity(List<T> objects) {
        try {

            Iterator iter = objects.iterator();
            int i = 0;
            while (iter.hasNext()) {
                T obj = (T) iter.next();
                entityManager.merge(obj);
                i++;
                if (i > 100) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 删除单个实体
     */
    @Override
    public boolean deleteEntity(T object) {
        try {

            entityManager.remove(entityManager.merge(object));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * 批量删除
     *
     * @param list
     * @return
     * @throws RADPException
     */
    protected boolean deleteEntity(List<T> list) {
        try {

            for (int i = 0; i < list.size(); i++) {
                T t = entityManager.merge(list.get(i));
                entityManager.remove(t);
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     *
     *
     * @param jpql
     * @param isNameQuery
     * @return
     * @throws RADPException
     */
    public List<T> queryAllEntity(String jpql, boolean isNameQuery) {
        try {
            Query q = null;
            if (isNameQuery) {
                q = entityManager.createNamedQuery(jpql);
            } else {
                q = entityManager.createQuery(jpql);
            }
            return q.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取JDBC Connction 连接(不推荐使用)
     *
     * @return
     */
    protected Connection jdbcConn() {
        Connection connection = entityManager.unwrap(java.sql.Connection.class);
        return connection;
    }

    @Override
    public boolean removeBetchEntity(List entities) {
        try {

            for (int i = 0; i < entities.size(); i++) {
                Object obj = entityManager.merge(entities.get(i));
                entityManager.remove(obj);
                if (i % 100 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }

            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public boolean updateEntity(Object entity) {
        try {
            entityManager.merge(entity);
            return true;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public void clear() {
       entityManager.clear();
       entityManager.getEntityManagerFactory().getCache().evictAll();

    }
    /**
     * 
     * @param type
     * @param jpql
     * @param clazz
     * @return 
     */
    @Override
    public Query getJPAQuery(QueryType type, String jpql, Class<T> clazz) {
        Query query = null;
        switch (type) {
            case NamedQuery:
                query = entityManager.createNamedQuery(jpql, clazz);
                break;
            case JPQL:
                query = entityManager.createQuery(jpql, clazz);
                break;
            case NavtionSQL:
                query = entityManager.createNativeQuery(jpql, clazz);
                break;
            default:
                query = entityManager.createNamedQuery(jpql, clazz);
        }
        return query;
    }

    @Override
    public List<T> queryEntities(QueryConditionEntity qce, Class<T> clazz) {
        try {
            Query q = getJPAQuery(qce.getType(), qce.getJpql(), clazz);
            if (!qce.getType().equals(QueryType.NavtionSQL)){
                String[] columns = qce.getConditionCoulmns();
                DataType[] types = qce.getConditionCoulmnsTypes();
                Object[] values = qce.getConditionValues();
                PageCond cond = qce.getCond();
                for (int i = 0; i < columns.length; i++) {
                    switch (types[i]) {
                        case Number:
                            q.setParameter(columns[i],
                                    Integer.parseInt(values[i].toString()));
                            break;
                        case String:
                            q.setParameter(columns[i], values[i].toString());
                            break;
                        case Date:
                            break;
                        case Object:
                            q.setParameter(columns[i], values[i]);
                        case Boolean:
                            q.setParameter(columns[i], Boolean.parseBoolean(values[i].toString()));
                            break;
                        default:
                            q.setParameter(columns[i], values[i]);
                    }

                }

                if (cond != null) {
                    q.setMaxResults(cond.getPageNum()).setFirstResult(cond.getStartPage());
                }
            }
            return q.getResultList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    @Override
    public T querySingleEntity(Class c, java.lang.Long primaryKey) {
        return (T) entityManager.find(c, primaryKey);
    }

    @Override
    public Query getJPAQuery(QueryType type, String jpql) {
        Query query = null;
        switch (type) {
            case NamedQuery:
                query = entityManager.createNamedQuery(jpql);
                break;
            case JPQL:
                query = entityManager.createQuery(jpql);
                break;
            case NavtionSQL:
                query = entityManager.createNativeQuery(jpql);
                break;
            default:
                query = entityManager.createNamedQuery(jpql);
        }
        return query;
    }

    
    @Override
    public DatabaseType getDatabaseType() {
//        Map properities =entityManager.getProperties();
//        String tempStr = (String) properities.get("javax.persistence.jdbc.url");
//        if(tempStr.indexOf("mysql")>0){
//            return  DatabaseType.MYSQL;
//        }else if(tempStr.indexOf("oracle")>0){
//           return  DatabaseType.ORACLE;
//        }else if(tempStr.indexOf("sqlserver")>0){
//            return  DatabaseType.MSSQLSVER;
//        }else{
//             return  DatabaseType.UNKOWN;
//        }
         return DatabaseType.MYSQL;
    }
    /**
     * 分页查询
     * @param type
     * @param jpql
     * @param clazz
     * @param startRow
     * @param pageSize
     * @return 
     */
    @Override
    public List queryEntitiesByPage(QueryType type, String jpql, Class<T> clazz,int startRow,int pageSize) {
           Query query =  this.getJPAQuery(type, jpql, clazz);
           query.setFirstResult(startRow);
           query.setMaxResults(pageSize);
           return query.getResultList();
    }
    /**
     * 查询总条数
     * @param type
     * @param jpql
     * @return 
     */

    @Override
    public int queryCount(QueryType type, String jpql) {
        Query query =  this.getJPAQuery(type, jpql);
        Object obj =  query.getSingleResult();
        if(obj == null || obj.equals("")){
            obj = "0";
        }
        return  Integer.parseInt(obj.toString());
    }

    @Override
    public void openTransaction() {
       entityManager.getTransaction().begin();
    }
    

 
}
