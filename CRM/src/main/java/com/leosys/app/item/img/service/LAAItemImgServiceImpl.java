/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.img.service;

import com.leosys.app.item.entity.LAAItemImg;
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
@Service("laaItemImgService")
@Transactional
public class LAAItemImgServiceImpl extends BaseServiceImplement<LAAItemImg> implements  LAAItemImgService{
     public List<LAAItemImg> queryImgsByItemId(Long itemId){
      Query query = getJPAQuery(QueryType.JPQL, "select t from LAAItemImg t where t.itemId="+itemId);
        return query.getResultList();
     }
    
}
