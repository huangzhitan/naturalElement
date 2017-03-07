/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.service;

import com.leosys.app.item.entity.LAAItem;
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
@Service("laaItemService")
@Transactional
public class LAAItemServiceImpl extends BaseServiceImplement<LAAItem> implements LAAItemService{
     public List findAllItems(){
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAItem t    order by t.createTime, t.orderNo");
        return query.getResultList();
     
     }
     
     
}
