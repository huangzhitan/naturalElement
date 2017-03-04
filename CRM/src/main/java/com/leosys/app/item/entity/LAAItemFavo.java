package com.leosys.app.item.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "leosys_item_favo")
public class LAAItemFavo implements Serializable {
	 @Id
	 @TableGenerator(name = "LAAItemFavo", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "LAA_ITEM_FAVO_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAItemFavo")
	 @Column(name = "favoid")
	 private Long favoId;
	 @Column(name = "itemid", length = 10, nullable = false)
	 private Long itemId ;
	 @Column(name = "userid", length = 10, nullable = false)
	 private Long userId ;
	 @Column(name = "isdel", nullable = false)
	 private byte isDel = 0;
	 @Column(name="createtime")
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date createTime = new Date();
	public Long getFavoId() {
		return favoId;
	}
	public void setFavoId(Long favoId) {
		this.favoId = favoId;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public byte getIsDel() {
		return isDel;
	}
	public void setIsDel(byte isDel) {
		this.isDel = isDel;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	 
	 

}
