/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.type.service;

import com.leosys.app.item.entity.LAAType;
import com.leosys.core.common.QueryType;
import com.leosys.core.service.BaseServiceImplement;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fanyouyong
 */
@Service("laatypeService")
@Transactional
public class LAATypeServiceImpl extends  BaseServiceImplement<LAAType> implements LAATypeService{
      public List findAllTypes(){
       Query query = getJPAQuery(QueryType.JPQL, "select t from LAAType t where t.isDel = 0   order by t.orderNo");
        return query.getResultList();
        
      }
    /**
     * 找到所有父节点的菜单
     * @return 
     */
    public List finAllParentTypes(){
      Query query = getJPAQuery(QueryType.JPQL, "select t from LAAType t where t.isDel = 0 and t.isParent=1  order by t.orderNo");
        return query.getResultList();
    }
     public List findAllChildTypesByPId(Long pid) {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAType t where t.isDel = 0 and  t.isParent = 0 and t.parentNaviId="+pid+" order by t.orderNo");
        return query.getResultList();
    }
}
