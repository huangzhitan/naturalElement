/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author fanyouyong
 */
@Entity
@Table(name = "leosys_item")
public class LAAItem implements Serializable {
    @Id
    @TableGenerator(name = "LAAItem", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "MY_ITEM_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAItem")
    @Column(name = "itemid")
    private long itemId;
    @Column(name = "itemname", length = 50, nullable = false)
    private String itemName = "";
    @Column(name = "detail", length = 255, nullable = false)
    private String detail = "";
    @Column(name = "isdel", nullable = false)
    private byte isDel = 0;
    @Column(name = "isimp", nullable = false)
    private byte isImp = 0;
    @Column(name = "orderno", nullable = false)
    private long orderNo = 0;
    @Column(name = "types",nullable = false)
    private String types = "";
    @Column(name = "pubimg", nullable = false)
    private String pubimg  = "";
    @Column(name = "createtime", length = 255)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime = new Date();
    @Column(name = "longs", nullable = false)
    private long longs = 0;
    @Column(name = "weidth", nullable = false)
    private long weidth = 0;
    @Column(name = "height", nullable = false)
    private long height = 0;
    @Column(name = "nums", nullable = false)
    private long nums = 0;
    @Column(name = "fprice", nullable = false)
    private long fprice = 0;
    @Column(name = "sprice", nullable = false)
    private long sprice = 0;
    @Column(name = "tprice", nullable = false)
    private long tprice = 0;
    @Column(name = "status", nullable = false)
    private int status = 0;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public byte getIsDel() {
        return isDel;
    }

    public void setIsDel(byte isDel) {
        this.isDel = isDel;
    }

    public byte getIsImp() {
        return isImp;
    }

    public void setIsImp(byte isImp) {
        this.isImp = isImp;
    }

    public long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(long orderNo) {
        this.orderNo = orderNo;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getPubimg() {
        return pubimg;
    }

    public void setPubimg(String pubimg) {
        this.pubimg = pubimg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public long getLongs() {
        return longs;
    }

    public void setLongs(long longs) {
        this.longs = longs;
    }

    public long getWeidth() {
        return weidth;
    }

    public void setWeidth(long weidth) {
        this.weidth = weidth;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public long getNums() {
        return nums;
    }

    public void setNums(long nums) {
        this.nums = nums;
    }

    public long getFprice() {
        return fprice;
    }

    public void setFprice(long fprice) {
        this.fprice = fprice;
    }

    public long getSprice() {
        return sprice;
    }

    public void setSprice(long sprice) {
        this.sprice = sprice;
    }

    public long getTprice() {
        return tprice;
    }

    public void setTprice(long tprice) {
        this.tprice = tprice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
            
    

   
}
