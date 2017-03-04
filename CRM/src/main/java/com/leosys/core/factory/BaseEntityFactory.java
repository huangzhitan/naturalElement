package com.leosys.core.factory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.log4j.Logger;


/**
 *
 * @author Administrator
 */
public class BaseEntityFactory {
//    
//    static Logger logger = Logger.getLogger(BaseEntityFactory.class);
//
//    private   String defaultUnit = "com.leosys_journal_war_1.0-SNAPSHOTPU";
//    
//    private  BaseEntityFactory (){
//         
//    }
//    
//    private static BaseEntityFactory entityFactory = new BaseEntityFactory();
//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//    private String persistenceUnitName;
//    public synchronized static BaseEntityFactory  getInstance(){
//        if(entityFactory==null){
//            entityFactory = new BaseEntityFactory();
//        } 
//        return entityFactory;
//    }
//    
//    public  EntityManagerFactory getEntityManagerFactory(String persistenceUnit,Map persistenceUnitProperties){
//        if(entityFactory!=null){
//            if(emf == null){
//                if("".equals(persistenceUnit)){
//                    persistenceUnit = defaultUnit;
//                }
//                emf = Persistence.createEntityManagerFactory(persistenceUnit, persistenceUnitProperties);
//             //   EntityManagerFactoryImpl emfi = (EntityManagerFactoryImpl) emf.getPersistenceUnitUtil();
//              //  session = emfi.getServerSession();
//                this.persistenceUnitName = persistenceUnit;
//            }
//        }
//        return emf;
//   }
//    
//   public  EntityManagerFactory getEntityManagerDefaultFactory(){
//        if(entityFactory!=null){
//            if(emf == null){
//                emf = Persistence.createEntityManagerFactory(defaultUnit);
//                this.persistenceUnitName = defaultUnit;
//            }
//        }
//        return emf;
//   }
//    public EntityManager getEntityManager() {
//        if (em == null) {
//            if (emf != null) {
//                em = emf.createEntityManager();
//            } else {
//                emf = getEntityManagerDefaultFactory();
//                em = emf.createEntityManager();
//            }
//        }
//        return em;
//    }
//    
//    public  void close(){
//        if(em!=null){
//            em.close();
//            em = null;
//        }
//        if(emf!=null){
//            emf.close();
//            emf = null;
//        }
//        entityFactory = null;
//    }
//
//    public String getPersistenceUnitName() {
//        return persistenceUnitName;
//    }
//
//    public String getDefaultUnit() {
//        return defaultUnit;
//    }
//
//    public void setDefaultUnit(String defaultUnit) {
//        this.defaultUnit = defaultUnit;
//    }
//    
    
}
