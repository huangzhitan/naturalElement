/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laauser.service;

import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-07 16:39
 */
public interface LAAUserService extends BaseService<LAAUser> {
    /**
     * 查找所有用户
     * @return 
     */
    public List findAllLaaUsers();
    
    public LAAUser getSingleUserByUname(String uName);
    
    public LAAUser getSingleUserByPhone(String phone);
    
    public List<LAAUser>  getUsersByLevel(Integer level);
    
//    public boolean moveToRecycle(long userId, long uId);
}
