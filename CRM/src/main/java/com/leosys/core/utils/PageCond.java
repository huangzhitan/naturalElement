/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;

import java.util.List;

/**
 *
 * @author sam
 */
public class PageCond{

    
    /**
     * 起始条数
     */
    private int startPage = 0 ;
    
    
    /**
     * 下一页
     */
    private int nextPage;
    
    /**
     * 上一页
     */
    private int frontPage;
    
    /**
     * 页大小
     */
    private int pageNum = 10;
    
    /**
     * 总条数
     */
    private int totalCount;
    
    /**
     * 总页数
     */
    private int totalPageCount;

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }



    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        this.totalPageCount = totalCount / pageNum;
        if(totalCount % pageNum !=0){
            this.totalPageCount ++;
        }
        this.frontPage = this.startPage --;
        this.nextPage = this.startPage ++;
        if(this.frontPage <=0){
            this.frontPage = 0;
        }
        if(this.nextPage >=this.totalPageCount){
            this.nextPage = 0;
        } 
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
    
    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getFrontPage() {
        return frontPage;
    }

    public void setFrontPage(int frontPage) {
        this.frontPage = frontPage;
    }

    
    
    
    
    
    
    
    
}
