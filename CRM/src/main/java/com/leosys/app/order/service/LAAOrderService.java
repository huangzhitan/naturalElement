/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.order.service;

import com.leosys.app.item.entity.LAAOrder;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author fanyouyong
 */
public interface  LAAOrderService extends  BaseService<LAAOrder>{
     public List<LAAOrder> queryCancels();
    
}
