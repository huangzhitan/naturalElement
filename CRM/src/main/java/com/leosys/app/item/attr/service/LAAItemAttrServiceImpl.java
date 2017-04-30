/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.attr.service;

import com.leosys.app.item.entity.LAAItemAttr;
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
@Service("laaItemAttrService")
@Transactional
public class LAAItemAttrServiceImpl extends BaseServiceImplement<LAAItemAttr> implements LAAItemAttrService{
  
    @Override
    public List<LAAItemAttr> queryAttrsByItemId(Long itemId) {
         Query query = getJPAQuery(QueryType.JPQL, "select t from LAAItemAttr t where t.itemId="+itemId);
        return query.getResultList();
    }
}
