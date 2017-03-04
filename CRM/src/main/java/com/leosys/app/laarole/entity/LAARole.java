/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laarole.entity;

import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.app.laauser.entity.LAAUser;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 *
 * @author wenjie
 */
@Entity
@Table(name = "leosys_role")
public class LAARole implements Serializable {

    @Id
    @TableGenerator(name = "LAARole", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "roleid", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAARole")
    @Column(name = "roleid")
    private long roleId;
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "role_desc", length = 255, nullable = false)
    private String desc;
    @Column(name = "isdel", nullable = false)
    private byte isDel = 0;
    @Column(name = "level",nullable=false)
    private int level=1;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    private List<LAAUser> users;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "leosys_role_leosys_navi",
            joinColumns = {@JoinColumn(name = "LAARole_roleid",referencedColumnName = "roleid")},
            inverseJoinColumns = {@JoinColumn(name = "navis_naviid",referencedColumnName = "naviid")})
    private List<LAANavi> navis;
    
    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte getIsDel() {
        return isDel;
    }

    public void setIsDel(byte isDel) {
        this.isDel = isDel;
    }

    public List<LAANavi> getNavis() {
        return navis;
    }

    public void setNavis(List<LAANavi> navis) {
        this.navis = navis;
    }

    public List<LAAUser> getUsers() {
        return users;
    }

    public void setUsers(List<LAAUser> users) {
        this.users = users;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

  

}
