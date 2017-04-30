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
@Table(name = "leosys_order")
public class LAAOrder implements Serializable {
	
	 @Id
	 @TableGenerator(name = "LAAOrder", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "LAA_ORDER_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAOrder")
	 @Column(name = "orderid")
	 private Long orderId;
	 
	 @Column(name = "itemid")
	 private Long itemId;
	 
	 @Column(name = "userid")
	 private Long userId;
	 
	 @Column(name = "lxrid")
	 private Long lxrId;//联系人id
	 
	 @Column(name = "orderno")
	 private  String orderNo="";
	 
	 @Column(name = "payprice")
	 private  Double payPrice=0.0;//金额
	 
	 @Column(name = "paynum")
	 private  Integer payNum=0;//数量
	 
	 
	 @Column(name="activetime")
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date activeTime = new Date();//创建时间
	 
	 @Column(name="paytype")
	 private  byte payType=0;//0支付宝，1微信，2线下
	 
	 @Column(name="status")
	 private  byte status=0;//0待支付，1待发，2，待收3已收，4支付失败，5退款
	 
	 @Column(name="paytime")
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date payTime ;//支付时间时间
	 
	 @Column(name="iscancel")
	 private  byte isCancel=0;//0未取消1已取消
         
          
	 
	 @Column(name = "payno")
	 private  String payNo="";//支付宝微信返回的单号
       @Column(name = "address")
	 private  String address="";
       
       @Column(name = "phoneno")
	 private  String phoneNo="";
         @Column(name = "lxrname")
	 private  String lxrName="";
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getLxrId() {
		return lxrId;
	}

	public void setLxrId(Long lxrId) {
		this.lxrId = lxrId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(Double payPrice) {
		this.payPrice = payPrice;
	}

	public Integer getPayNum() {
		return payNum;
	}

	public void setPayNum(Integer payNum) {
		this.payNum = payNum;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public byte getPayType() {
		return payType;
	}

	public void setPayType(byte payType) {
		this.payType = payType;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public byte getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(byte isCancel) {
		this.isCancel = isCancel;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLxrName() {
        return lxrName;
    }

    public void setLxrName(String lxrName) {
        this.lxrName = lxrName;
    }

  
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 

}
