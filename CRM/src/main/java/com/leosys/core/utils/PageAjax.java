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
    private Integer pageSize=10;
    private Integer count;
    private String limit="";
    
    
//     public  $totaPage;
//    public  $currentPage;
//    public $pageList = array();
//    public  $header;
//    public  $count=0;
//    public $limit;
//    public $pageSize=10;
   public PageAjax(Integer $total, Integer $currentPage, Integer $pageSize){
			this.count=$total;
			this.pageSize=$pageSize;
			this.currentPage=$currentPage!=null ? (int)$currentPage : 1;
                        if(this.count%this.pageSize==0){
                            this.totaPage=this.count/this.pageSize;
                        }else{
                         this.totaPage=(this.count/this.pageSize)+1;
                        }
			
			this.limit=this.setLimit();
		}
    private  String setLimit(){
			return " Limit "+(this.currentPage-1)*this.pageSize+","+ this.pageSize;
		}            
    
    //put your code here
    public void setTotalpage1(Integer count){
        if(count==null)
            count=0;
        if(count==0){
        this.totaPage=0;
        }
        if(0<count){
        if(count%this.getPageSize()>0){
        this.totaPage=count/this.getPageSize()+1;
        }else{
         this.totaPage=count/this.getPageSize();
        }
        }
    
    }
    
    
    

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

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
    public int getStart(Integer page){
    if(page==null || page==0){
    page=1;
    }
    return page*this.getPageSize();
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    
    
    
    
}
