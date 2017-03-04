/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.ajax;

import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author sam
 */
public class AjaxReturn {
    
    private boolean status; 
    private String title ="提示" ;
    private String content ="操作成功";
    
    private String btnNext = "继续编辑";//回调弹窗按钮名称
    private String btnClose = "关闭返回"; //回调弹窗按钮名称
    
    private Map<String,Object> params = new HashMap<String, Object>();
    
    public AjaxReturn(){}

    public AjaxReturn(boolean status) {
        this.status = status;
    }
    
    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
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
    
    
    
    
}
