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
 * 类别表
 * @author Administrator
 *
 */
@Entity
@Table(name = "leosys_type")
public class LAAType implements Serializable {
	 @Id
	 @TableGenerator(name = "LAAType", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "typeid", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAType")
	 @Column(name = "typeid")
	 private Long typeId;
	 @Column(name = "typename", length = 50, nullable = false)
	 private String typeName = "";
	 @Column(name = "isdel", nullable = false)
	 private byte isDel = 0;
	 @Column(name = "orderno", nullable = false)
	 private long orderNo = 0;
	 @Column(name = "parentid")
	 private long parentId = 0;
	 @Column(name = "isparent", nullable = false)
	 private byte isParent = 0;
         
         @Column(name = "img", length = 255, nullable = false)
	 private String img = "";

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
         
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public byte getIsParent() {
		return isParent;
	}
	public void setIsParent(byte isParent) {
		this.isParent = isParent;
	}
	 

}
