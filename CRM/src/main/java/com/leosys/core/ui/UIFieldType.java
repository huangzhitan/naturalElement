
package com.leosys.core.ui;



/**
 *
 * @author sam
 */
public enum UIFieldType{
    Button("按钮"),Select("下拉框"),Text("文本框"),Ueditor("富文本编辑器"),
    Datepicker("日期"),Spinner("自增长"),
    Password("密码"),Checkbox("复选框"),RadioBox("单选框"),DIYUI("自定义UI");
    
    final String value;
     UIFieldType(String value){
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
        if("DIYUI".equals(name())){
            return "DIYUI";
        }else{
            return "UI";
        }
    }

}
