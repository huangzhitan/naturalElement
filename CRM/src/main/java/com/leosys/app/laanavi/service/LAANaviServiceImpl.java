/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laanavi.service;

import com.leosys.app.laanavi.entity.LAANavi;

import com.leosys.core.common.QueryType;
import com.leosys.core.service.BaseServiceImplement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 系统导航菜单服务接口实现
 *
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-11 18:48
 */
@Service("laaNaviService")
@Transactional
public class LAANaviServiceImpl extends BaseServiceImplement<LAANavi> implements LAANaviService {
    
    
    @Override
    public List findAllNavis() {

        Query query = getJPAQuery(QueryType.JPQL, "select t from LAANavi t where t.isDel = 0   order by t.orderNo");
        return query.getResultList();
    }

    @Override
    public List finAllParentNavis() {

        Query query = getJPAQuery(QueryType.NamedQuery, "findParentNavi.byOrderNo");
        List list = query.getResultList();
        return list;
    }

    @Override
    public List findAllChildNavis() {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAANavi t where t.isDel = 0 and  t.isParent = 0 and t.parentNaviId!=51l order by t.orderNo");
        return query.getResultList();
    }
    
    public List findAllChildNavisByPId(Long pid) {
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAANavi t where t.isDel = 0 and  t.isParent = 0 and t.parentNaviId="+pid+" order by t.orderNo");
        return query.getResultList();
    }

    @Override
//    public boolean moveToRecycle(long naviId, long uId) {
//        LAANavi navi = querySingleEntity(LAANavi.class, naviId);
//        navi.setIsDel(Byte.valueOf("1"));
//        
//        return (update(navi) && laaRecycleService.delResource(navi.getNaviId(), "naviId", "LAANavi", navi.getNaviName(), "系统菜单", uId));
//    }
    /**
     * 获取子菜单根据fuID
     * 
     * @param navis
     * @return 
     */
    public List queryClistByParentIds(List<LAANavi> navis){
        if(navis!=null&&navis.size()>0){
            List PIds = new ArrayList();
            for(LAANavi navi:navis){
            PIds.add(navi.getNaviId());
            }
            
              Query query = getJPAQuery(QueryType.JPQL, "select t from LAANavi t where t.isDel = 0 and  t.isParent = 0 and t.parentNaviId in :ids order by t.orderNo");
              query.setParameter("ids", PIds);
        return query.getResultList();
        
        }
        return new ArrayList();
    
    
    }

    

}
