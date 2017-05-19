/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.order.service;

import com.leosys.app.item.entity.LAAOrder;
import com.leosys.core.common.QueryType;
import com.leosys.core.service.BaseService;
import com.leosys.core.service.BaseServiceImplement;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fanyouyong
 */
@Service("laaOrderService")
@Transactional
public class LAAOrderServiceImpl extends BaseServiceImplement<LAAOrder> implements LAAOrderService{
    public List<LAAOrder> queryCancels(){
     Calendar calendar = Calendar.getInstance();  
    calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 6);  
    Date date = calendar.getTime();
        Query query = getJPAQuery(QueryType.JPQL, "select t from LAAOrder t where  t.isCancel=0 and t.status=0 and t.activeTime<=:date  ");
       query.setParameter("date", date);
       return query.getResultList();
    }
       
       
}
