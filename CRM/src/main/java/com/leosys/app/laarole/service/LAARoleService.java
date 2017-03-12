/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laarole.service;

import com.leosys.app.laarole.entity.LAARole;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-08 17:15
 */
public interface LAARoleService  extends BaseService<LAARole>{
    /**
     * 查询所有角色
     * @return 
     */
    public List findAllRoles();
    
    /**
     * 将角色移入回收站
     * @param roleId 角色ID
     * @return 
     */
//    public boolean moveRoleToRecycle(long roleId);
     /**
     * 查询所有角色
     * @return 
     */
    public List findAllRolesByLevel(int level);
    
}
