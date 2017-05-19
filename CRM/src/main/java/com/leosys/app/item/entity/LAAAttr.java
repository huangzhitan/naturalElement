/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author fanyouyong
 */
@Entity
@Table(name = "leosys_attr")
public class LAAAttr implements Serializable {
	 @Id
	 @TableGenerator(name = "LAAAttr", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "attrid", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAAttr")
	 @Column(name = "attrid")
	 private Long attrId;
         
         @Column(name = "typeid")
	 private Long typeId;
	 @Column(name = "attrname", length = 50, nullable = false)
	 private String attrName = "";
	 @Column(name = "isdel", nullable = false)
	 private byte isDel = 0;
	 @Column(name = "orderno", nullable = false)
	 private long orderNo = 0;
         @Column(name = "uitype", nullable = false)
	 private long uiType = 0;
         
         @Column(name = "initvalue", length = 50, nullable = false)
	 private String initValue = "";
	
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	
	public byte getIsDel() {
		return isDel;
	}
	public void setIsDel(byte isDel) {
		this.isDel = isDel;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}

    public Long getAttrId() {
        return attrId;
    }

    public void setAttrId(Long attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public long getUiType() {
        return uiType;
    }

    public void setUiType(long uiType) {
        this.uiType = uiType;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }

	 
}
