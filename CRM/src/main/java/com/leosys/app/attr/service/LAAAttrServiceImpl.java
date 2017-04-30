/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.attr.service;

import com.leosys.app.item.entity.LAAAttr;
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
@Service("laaAttrService")
@Transactional
public class LAAAttrServiceImpl extends BaseServiceImplement<LAAAttr>implements LAAAttrService {
     public List findAllAttrs(){
    
       Query query = getJPAQuery(QueryType.JPQL, "select t from LAAAttr t where t.isDel = 0   order by t.orderNo");
        return query.getResultList();
         
     }
}
