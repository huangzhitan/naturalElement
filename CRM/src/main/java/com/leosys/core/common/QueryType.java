/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.common;

/**
 *
 * @author sam
 */
 public enum QueryType{
            /**
             * //JPA NamedQuery 查询方式 推荐使用
             */
            NamedQuery, 
            /**
             * //JPQL 查询方式
             */
            JPQL, 
            /**
             * 原生SQL 方式
             */
            NavtionSQL,
            
}
