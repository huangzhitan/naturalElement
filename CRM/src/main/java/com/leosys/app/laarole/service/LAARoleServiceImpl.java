/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laarole.service;


import com.leosys.app.laarole.entity.LAARole;
import com.leosys.core.common.QueryType;
import com.leosys.core.service.BaseServiceImplement;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wenjie
 */
@Service("laaRoleService")
@Transactional
public class LAARoleServiceImpl extends BaseServiceImplement<LAARole> implements LAARoleService {

   
  

    @Override
    public List findAllRoles() {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAARole t where t.isDel = 0   order by t.name");
        return query.getResultList();
    }

    
//    public boolean moveRoleToRecycle(long roleId) {
//        LAARole role = querySingleEntity(LAARole.class, roleId);
//        role.setIsDel(Byte.valueOf("1"));
//        //将role表中的isdel字段改为1，并在recycle表添加记录
//        boolean rs = (update(role)
//                && laaRecycleService.delResource(role.getRoleId(), "roleid", "LAARole", role.getName(), "系统角色", 10));
//        return rs;
//    }
    
     public List findAllRolesByLevel(int level){
       Query query = getJPAQuery(QueryType.JPQL, "select t from LAARole t where t.isDel = 0 and t.level="+level+"  order by t.name");
        return query.getResultList();
     }
}
