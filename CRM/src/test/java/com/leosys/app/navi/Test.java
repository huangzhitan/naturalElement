/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.navi;

import com.leosys.app.laarole.entity.LAARole;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author fanyouyong
 */
public class Test {
      public static void main(String[] args) throws Exception {  
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("com.leosys_journal_war_1.0-SNAPSHOTPU");  
        EntityManager em = factory.createEntityManager();  
          
          
      TypedQuery <LAARole> q=  em.createQuery("select c from LAARole c", LAARole.class);
       List<LAARole> roles=q.getResultList();
       System.out.println(roles.size());
        }
}
