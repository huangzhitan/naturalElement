/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.ui;

import java.util.TreeMap;


/**
 *
 * @author sam
 */
public abstract class BaseUI {
    
    protected  UIFieldType uiType;
    protected  UIComponentType uiCompType;
    protected  String label; //组件label显示
    protected  String htmlProperty; //html 属性值
    protected  String dataProperty; //数据源属性值
    protected  String uid; // 组件唯一标识

    /**
     * 对应FreeMarker 模版组件的属性
     */
    private final TreeMap<String,String> uiProperty = new TreeMap <String, String>();

    /**
     * 初始化UI组件属性
     */
    public abstract void initUIAttr();
    
   
    public BaseUI(UIFieldType uiType){
        this.uiType = uiType;
        initUIAttr();
    }
     public BaseUI(UIComponentType uiCompType){
        this.uiCompType = uiCompType;
    }
     /**
     * 对应FreeMarker 模版组件名称
     * @return 
     */
    public abstract String toUIName();

    public   UIFieldType getUiType() {
        return uiType;
    }

    public TreeMap<String, String> getUiProperty() {
        return uiProperty;
    }

    public void addUiProperty(String property,String defaultValue) {
       this.uiProperty.put(property, defaultValue);
    }
    
    public void removeUiProperty(String proKey) {
       this.uiProperty.remove(proKey);
    }

    
    
    
    
}
