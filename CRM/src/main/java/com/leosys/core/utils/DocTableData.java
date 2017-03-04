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
 * @author zhuangweicheng
 */
public class DocTableData {
    private String[] header;
    private String title;
    private List<Map<String,Object>> table = new ArrayList<Map<String,Object>>();
    private int cols;

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
    

    public String[] getHeader() {
        return header;
    }

    public void setHeader(String[] header) {
        this.header = header;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Map<String,Object>> getTable() {
        return table;
    }

    public void setTable(List<Map<String,Object>> table) {
        this.table = table;
    }
    
}
