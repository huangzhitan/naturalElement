/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fanyy
 */
public class PageAjax {
    private Integer totaPage;
    private Integer currentPage;
    private List<Map<String,Object>> pageList = new ArrayList<Map<String,Object>>();
    private String header;
    private Integer count=0;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
  

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }
    

    public Integer getTotaPage() {
        return totaPage;
    }

    public void setTotaPage(Integer totaPage) {
        this.totaPage = totaPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public List<Map<String, Object>> getPageList() {
        return pageList;
    }

    public void setPageList(List<Map<String, Object>> pageList) {
        this.pageList = pageList;
    }

    
    
    
}
