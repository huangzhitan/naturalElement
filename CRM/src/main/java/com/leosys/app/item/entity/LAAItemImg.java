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
@Table(name = "leosys_item_img")
public class LAAItemImg implements Serializable {
    @Id
    @TableGenerator(name = "LAAItemImg", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "MY_ITEM_IMG_ID", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAItemImg")
    @Column(name = "imgid")
    private long imgId; 
    @Column(name = "itemid",nullable = false)
    private long itemId; 
     @Column(name = "url",nullable = false)
    private String url; 

    public long getImgId() {
        return imgId;
    }

    public void setImgId(long imgId) {
        this.imgId = imgId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
     
    
    
    
}
