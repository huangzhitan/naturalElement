/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.dao;


import com.leosys.core.common.QueryType;
import com.leosys.core.ui.PageArr;
import com.leosys.core.utils.PageCond;
import java.util.List;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 *
 * @author Administrator
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/applicationContext-sys.xml",
    "file:src/main/webapp/WEB-INF/applicationContext-item.xml",
    "file:src/main/webapp/WEB-INF/applicationContext-stat.xml"})
@Transactional      
public class BaseDAOImplementTest {
    
  
    
    
    
    public BaseDAOImplementTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }




    /**
     * Test of getListByPage method, of class BaseDAOImplement.
     */
    @Test
    public void testGetListByPage() {
//        //第一种方法  分页查询
//        List<LAAType> list = commonService.queryEntitiesByPage(QueryType.NamedQuery, "findLAAType.byAll",LAAType.class,0,20);
//        System.out.println(list.size());
//        
//        //第二种方法 分页查询
//        Query query = commonService.getJPAQuery(QueryType.NamedQuery, "findLAAType.byAll",LAAType.class);
//        query.setFirstResult(0);
//        query.setMaxResults(20);
//        List<LAAType> list2 = query.getResultList();
//        System.out.println(list2.size());
//        
//        //封装PageArr 对象
//        int  count = commonService.queryCount(QueryType.NamedQuery, "LAAType.TotalCount");
//        PageArr pageArr = new PageArr();
//        pageArr.setCount(count);
        
        
        
        
    }

   
    
}
