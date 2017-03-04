/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.leosys.core.ui;




/**
 *
 * @author sam
 */
public enum UIComponentType {
    ButtonSet("按钮组"),Accordion("折叠"),Tabs("标签"),Navbar("导航菜单"),Autocomplete("自动提交");
    
       
     final String value;
     UIComponentType(String value){
        this.value = value;
    }
     
    @Override
    public String toString() {
        return this.value;
    }
    
        /**
     * 获取对应组件模版文件名称
     * @return 
     */
    public String getUITplName() {
         return "UIE";
    }
    
}
