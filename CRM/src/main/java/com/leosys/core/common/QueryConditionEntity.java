/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.common;

import com.leosys.core.common.DataType;
import com.leosys.core.utils.PageCond;

/**
 * @author Sam.Zheng <zcl1866@sina.com>
 * @date 2013-4-25 16:30:09
 * @version <v0.1>
 * @copyright Sam.zheng 封装查询实体条件
 */
public class QueryConditionEntity {
    

	private String jpql; //查询SQL
	private String[] conditionCoulmns; //参数
	private DataType[] conditionCoulmnsTypes; //参数类型
	private Object[] conditionValues; //参数值
        
        private PageCond cond; //分页条件
        
        /**
         * 查询类型，
         * 如果为NavtionSQL conditionCoulmns、conditionCoulmnsTypes、conditionValues 传空值即可
         */
        private QueryType type;
//        public QueryConditionEntity(QueryType type){
//            this.type = type;
//        }
        
	public String[] getConditionCoulmns() {
		return conditionCoulmns;
	}

	public void setConditionCoulmns(String[] conditionCoulmns) {
		this.conditionCoulmns = conditionCoulmns;
	}

	public DataType[] getConditionCoulmnsTypes() {
		return conditionCoulmnsTypes;
	}

	public void setConditionCoulmnsTypes(DataType[] conditionCoulmnsTypes) {
		this.conditionCoulmnsTypes = conditionCoulmnsTypes;
	}

	public Object[] getConditionValues() {
		return conditionValues;
	}

	public void setConditionValues1(Object[] conditionValues) {
		this.conditionValues = conditionValues;
	}

	public String getJpql() {
		return jpql;
	}

	public void setJpql(String jpql) {
		this.jpql = jpql;
	}

	/**
	 * JPQL语句中包含多个字段属性
         * 
	 * conditionCoulmns，conditionCoulmnsTypes，conditionValues数组索引要相一致
	 * 
     * @param type
    * @param jpql JPA SQL 语句
     * @param conditionCoulmns
     * @param conditionCoulmnsTypes
     * @param conditionValues
     * @param cond
	 */
	public QueryConditionEntity(QueryType type,String jpql, String[] conditionCoulmns,
			DataType[] conditionCoulmnsTypes, Object[] conditionValues,PageCond cond) {
                this.type = type;
		this.jpql = jpql;
		this.conditionCoulmns = conditionCoulmns;
		this.conditionCoulmnsTypes = conditionCoulmnsTypes;
		this.conditionValues = conditionValues;
                this.cond = cond;

	}

	/**
	 * JPQL语句查询只有一个字段属性
	 * 
	 * @param jpql JPA SQL 语句 
	 * @param conditionCoulmn 要查询的参数
	 * @param conditionCoulmnType 要查询的参数类型
	 * @param conditionValue 参数值
	 */
	public QueryConditionEntity(QueryType type,String jpql, String conditionCoulmn,
			DataType conditionCoulmnType, Object conditionValue,PageCond cond) {
                this.type = type;
		this.jpql = jpql;
		this.conditionCoulmns = new String[] { conditionCoulmn };
		this.conditionCoulmnsTypes = new DataType[] { conditionCoulmnType };
		this.conditionValues = new Object[] { conditionValue };
                this.cond = cond;

	}

    public QueryType getType() {
        return type;
    }

    public void setType(QueryType type) {
        this.type = type;
    }

    public PageCond getCond() {
        return cond;
    }

    public void setCond(PageCond cond) {
        this.cond = cond;
    }
        
        

}
