/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.ui;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huangzhitan
 */
public class PageArr {

    private int count;
    private int pageTotal;
    private int page;
    private String url;
    private int pageSize = 10;
    private List list;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public PageArr() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        if (page < 1) {
            page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageTotal() {
        pageTotal = (count + pageSize - 1) / pageSize;
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List getPageList() {
        if(page>pageTotal){
         page=pageTotal;
        }
        List pageResult = new ArrayList();
        if (page < pageTotal) {
            for (int i = (page - 1) * pageSize; i < page * pageSize; i++) {
                Object o = list.get(i);
                pageResult.add(o);
            }
        } else {
            int size = count % pageSize;
            if (size == 0 && count != 0) {
                size = pageSize;
            }

            for (int i = (page - 1) * pageSize; i < (page - 1) * pageSize + size; i++) {
                Object o = list.get(i);
                pageResult.add(o);
            }
        }
        return pageResult;
    }
}
