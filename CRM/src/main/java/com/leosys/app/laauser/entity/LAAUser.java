/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laauser.entity;

import com.leosys.app.laarole.entity.LAARole;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-07 11:32
 */
@Entity
@Table(name = "leosys_user")
@NamedQueries({
    @NamedQuery(name = "findLAAUser.byUName", query = "select t from LAAUser t where t.uName = :uName"),})
public class LAAUser implements Serializable {

    @Id
    @TableGenerator(name = "LAAUser", table = "leosys_generator", pkColumnName = "key_name", pkColumnValue = "uid", valueColumnName = "key_value", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "LAAUser")
    @Column(name = "uid")
    private Long uId;
    /**
     * 用户账号
     */
    @Column(name = "uname", length = 100, nullable = false)
    private String uName;
    /**
     * 用户密码 des 加密 不要md5*
     */
    @Column(name = "pass", length = 50, nullable = false)
    private String pass;
    /**
     * 用户真实姓名*
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    @Column(name = "email", length = 100, nullable = false)
    private String email = "0";
    @Column(name = "sex", nullable = false)
    private byte sex = 0;
    @Column(name = "addtime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime = new Date();
    @Column(name = "isdel", nullable = false)
    private byte isDel = 0;
    @Column(name="status",nullable=false)
    private int status=0;//
     @Column(name="phoneno",nullable=false)
    private String phoneNo;//电话号码
      @Column(name="isyan",nullable=false)
     private Integer isYan=0;
      @Column(name="yancode",nullable=false)
     private String yanCode;
      
    @ManyToMany()
    @JoinTable(name = "leosys_user_leosys_role",
            joinColumns = {@JoinColumn(name = "LAAUser_uid",referencedColumnName = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "roles_roleid",referencedColumnName = "roleid")})
    private List<LAARole> roles;
    public Long getuId() { 
        return uId;
    }

    public void setuId(Long uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte getSex() {
        return sex;
    }

    public void setSex(byte sex) {
        this.sex = sex;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public byte getIsDel() {
        return isDel;
    }

    public void setIsDel(byte isDel) {
        this.isDel = isDel;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public List<LAARole> getRoles() {
        return roles;
    }

    public void setRoles(List<LAARole> roles) {
        this.roles = roles;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo; 
    }

    public Integer getIsYan() {
        return isYan;
    }

    public void setIsYan(Integer isYan) {
        this.isYan = isYan;
    }

    public String getYanCode() {
        return yanCode;
    }

    public void setYanCode(String yanCode) {
        this.yanCode = yanCode;
    }

    
    
    
    
}
