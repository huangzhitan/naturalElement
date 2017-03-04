package com.leosys.core.ajax;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
/**
 *
 * @author sam
 */
public class AjaxPageReturn {
    
    private boolean status; 
    private String title ="提示" ;
    private String content ="操作成功";
    
    private String btnNext = "继续编辑";//回调弹窗按钮名称
    private String btnClose = "关闭返回"; //回调弹窗按钮名称
    
    private HashMap<String,Object> params = new HashMap<String, Object>();
    
    private String data;//返回json数据
    private int curPage;//当前页
    private int totalPage;//当前页
    private int totalCount;//总记录
    private String typeId;//产品类型
    
    public AjaxPageReturn(){}

    public AjaxPageReturn(boolean status) {
        this.status = status;
    }
    
    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    

    public HashMap<String, Object> getParams() {
        return params;
    }

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBtnNext() {
        return btnNext;
    }

    public void setBtnNext(String btnNext) {
        this.btnNext = btnNext;
    }

    public String getBtnClose() {
        return btnClose;
    }

    public void setBtnClose(String btnClose) {
        this.btnClose = btnClose;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

   
    
    
    
    
}
