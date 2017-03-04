/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.common;

/**
 *
 * @author sam
 */
public class DDLSQLCode {

    public  String VARCHAR;//字符类型
    public  String INT; //数字类型
    public  String DATE; //时间类型
    public  String CALCULATE;//计算类型
    public  String TEXT;//长文本类型
    public String BOOLEAN;
    
    public  String ADDCOlUMN; //新增字段
    
    public  String MODIFYCOLUMN; //修改字段
    
    public  String DROPCOLUMN;
    
    public  String CREATETABLEBYPRIMARYID ;//创建带主键的表
    
    public String DROPTABLE;
    

    
    
    private DatabaseType type;

    public DDLSQLCode(DatabaseType type) {
        this.type = type;
        initCode();
    }

    private void initCode() {
        if (this.type != null) {
            switch (this.type) {
                case MYSQL:
                    this.CALCULATE = "decimal";
                    this.INT = "int";
                    this.TEXT = "text";
                    this.DATE = "datetime";
                    this.VARCHAR = "varchar";
                    this.BOOLEAN = "tinyint";
                    this.ADDCOlUMN = "ALTER TABLE  %s add %s %s (%s) %s";
                    this.MODIFYCOLUMN = "ALTER TABLE  %s change  %s";
                    this.DROPCOLUMN = "ALTER TABLE %s DROP %s"; 
                    this.CREATETABLEBYPRIMARYID =  "CREATE TABLE `%s` (`ID` varchar(40) NOT NULL,PRIMARY KEY (`ID`)) ENGINE=InnoDB DEFAULT CHARSET=utf8";
                    this.DROPTABLE = "DROP TABLE IF EXISTS `%s` ";
                    
                    break;
                case ORACLE:
                    //。。。。。。。。。。。。。。。。
                    break;
                case MSSQLSVER:
                    break;
                default:
                    break;
            }
        }
    }
   
}
