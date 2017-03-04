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
@Table(name = "leosys_mess")
public class LAAMess implements Serializable {
	 @Id
	 @TableGenerator(name = "LAAMess", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "LAA_MESS_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAMess")
	 @Column(name = "messid")
	 private Long messId;
	 
	 @Column(name = "orderid", length = 10, nullable = false)
	 private Long orderId=-1l ;//订单id
	 
	 @Column(name = "itemid", length = 10, nullable = false)
	 private Long itemId=-1l ;//资源id
	 
	 @Column(name = "senderid", length = 10, nullable = false)
	 private Long senderId=-1l ;//发送者id默认系统
	 
	 @Column(name = "reciverid", length = 10, nullable = false)
	 private Long reciverId=-1l ;//接收者id默认
	
	 @Column(name = "messtitle", length = 10, nullable = false)
	 private String messTitle;//消息标题
	 
	 @Column(name = "content", length = 10, nullable = false)
	 private String content;//消息内容
	 
	 @Column(name = "messtype", nullable = false)
	 private byte messType = 0;//0系统消息，1订单消息，2求货消息,3上货消息
	 
	 @Column(name = "imgcontent", length = 10, nullable = false)
	 private String imgcontent;//图文消息图片路径
	 
	 @Column(name = "isread", nullable = false)
	 private byte isRead = 0;
	 
	 @Column(name = "isdel", nullable = false)
	 private byte isDel = 0;
	 
	 @Column(name="createtime")
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date createTime = new Date();

	public Long getMessId() {
		return messId;
	}

	public void setMessId(Long messId) {
		this.messId = messId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public Long getReciverId() {
		return reciverId;
	}

	public void setReciverId(Long reciverId) {
		this.reciverId = reciverId;
	}

	public String getMessTitle() {
		return messTitle;
	}

	public void setMessTitle(String messTitle) {
		this.messTitle = messTitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public byte getMessType() {
		return messType;
	}

	public void setMessType(byte messType) {
		this.messType = messType;
	}

	public String getImgcontent() {
		return imgcontent;
	}

	public void setImgcontent(String imgcontent) {
		this.imgcontent = imgcontent;
	}

	public byte getIsRead() {
		return isRead;
	}

	public void setIsRead(byte isRead) {
		this.isRead = isRead;
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
