/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.attr.service;

import com.leosys.app.item.entity.LAAAttr;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author fanyouyong
 */
public interface LAAAttrService extends  BaseService<LAAAttr>{
     public List findAllAttrs();
}
