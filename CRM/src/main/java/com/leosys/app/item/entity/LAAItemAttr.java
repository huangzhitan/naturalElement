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
@Table(name = "leosys_item_attr")
public class LAAItemAttr implements Serializable {
    @Id
    @TableGenerator(name = "LAAItemAttr", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "MY_ITEM_ATTR_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAItemAttr")
    @Column(name = "id")
    private long id; 
    @Column(name = "itemid",nullable = false)
    private long itemId; 
     @Column(name = "attrid",nullable = false)
    private Long attrId; 
      @Column(name = "attrname",nullable = false)
    private String attrName; 
      @Column(name = "value",nullable = false)
    private String value; 

 
    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

   
     
    
    
    
}
