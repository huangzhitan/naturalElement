/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laanavi.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 导航菜单
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-11 17:34
 */
@Entity
@Table(name = "leosys_navi")
@NamedQueries({
    @NamedQuery(name = "findParentNavi.byOrderNo", query = "select t  from LAANavi  t where t.isParent = 1 and t.isDel=0 order by t.orderNo"),
})
public class LAANavi implements Serializable {

    @Id
    @TableGenerator(name = "LAANavi", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "naviid", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAANavi")
    @Column(name = "naviid")
    private long naviId;
    @Column(name = "naviname", length = 50, nullable = false)
    private String naviName = "";
    @Column(name = "naviurl", length = 255, nullable = false)
    private String naviUrl = "";
    @Column(name = "isdel", nullable = false)
    private byte isDel = 0;
    @Column(name = "orderno", nullable = false)
    private long orderNo = 0;
    @Column(name = "parentnaviid")
    private long parentNaviId = 0;
    @Column(name = "isparent", nullable = false)
    private byte isParent = 0;
    @Column(name = "navi_icon", length = 255)
    private String naviIcon = "";
//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "navis")
//    private List<LAARole> roles;
    public long getNaviId() {
        return naviId;
    }

    public void setNaviId(long naviId) {
        this.naviId = naviId;
    }

    public String getNaviName() {
        return naviName;
    }

    public void setNaviName(String naviName) {
        this.naviName = naviName;
    }

    public String getNaviUrl() {
        return naviUrl;
    }

    public void setNaviUrl(String naviUrl) {
        this.naviUrl = naviUrl;
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

    public long getParentNaviId() {
        return parentNaviId;
    }

    public void setParentNaviId(long parentNaviId) {
        this.parentNaviId = parentNaviId;
    }

    public byte getIsParent() {
        return isParent;
    }

    public void setIsParent(byte isParent) {
        this.isParent = isParent;
    }

    public String getNaviIcon() {
        return naviIcon;
    }

    public void setNaviIcon(String naviIcon) {
        this.naviIcon = naviIcon;
    }

//    public List<LAARole> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<LAARole> roles) {
//        this.roles = roles;
//    }
    
}
