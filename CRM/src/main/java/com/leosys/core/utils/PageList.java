/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author fanyouyong
 */
public class PageList {
     public PageAjax $pageAjax;
  
     public String $sql;
    
    public PageList(JdbcTemplate jdbcTemplate ,String $sql, Integer $page,Integer $pageSize){
         
       Integer $count=jdbcTemplate.queryForInt("select count(*) from ("+$sql+") tt where 1=1");
       PageAjax $pageaj=new PageAjax($count, $page, $pageSize);
       List<Map<String,Object>> $rows= new ArrayList<Map<String,Object>>();
       $sql+=$pageaj.getLimit();
      
       $rows=jdbcTemplate.queryForList($sql);
       $pageaj.setPageList($rows);
       this.$pageAjax=$pageaj;
                         
		}
}
