/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laauser.service;


import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laauser.dao.LAAUserDAO;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.core.common.QueryType;
import com.leosys.core.dao.BaseDAO;
import com.leosys.core.service.BaseServiceImplement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-07 16:39
 */
@Service("laaUserService")
@Transactional
public class LAAUserServiceImpl extends BaseServiceImplement<LAAUser> implements LAAUserService {

    @Autowired
    private LAAUserDAO laaUserDAO;
   

    @Override
    public BaseDAO<LAAUser> getBaseDAO() {
        return laaUserDAO; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAllLaaUsers() {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAUser t where t.isDel = 0   order by t.addTime desc");
        List<LAAUser> users =query.getResultList();
        List delList = new ArrayList();
        for(LAAUser u:users){
            List<LAARole> roles = u.getRoles();
            if(roles !=null){
                for(LAARole r:roles){
                    if(r.getRoleId()==16){
                        delList.add(u);
                    }                 
                }
            }
        }
        users.removeAll(delList);
        return users;
    }

    @Override
    public LAAUser getSingleUserByUname(String uName) {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAUser t where t.isDel = 0 and t.uName = '" + uName + "'");
        LAAUser user;
        try {
            user = (LAAUser) query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;

    }
      @Override
    public LAAUser getSingleUserByPhone(String phoneNo) {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAUser t where t.isDel = 0 and t.phoneNo = '" + phoneNo + "'");
        LAAUser user;
        try {
            user = (LAAUser) query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        return user;

    }

//    @Override
//    public boolean moveToRecycle(long userId, long uId) {
//        LAAUser laaUser = querySingleEntity(LAAUser.class, userId);
//        laaUser.setIsDel(Byte.valueOf("1"));
//        
//        return (update(laaUser) && laaRecycleService.delResource(laaUser.getuId(), "uId", "LAAUser", laaUser.getName(), "系统用户", uId));
//    }

    @Override
    public List<LAAUser> getUsersByLevel(Integer level) {
         Query query = getJPAQuery(QueryType.NavtionSQL, "select t.*  from  leosys_user t join leosys_user_leosys_role t1 on(t.uid = t1.LAAUser_uid) join leosys_role t2 on(t1.roles_roleid=t2.roleid)");
        List<LAAUser> users =query.getResultList();
        return users;
    }

}
