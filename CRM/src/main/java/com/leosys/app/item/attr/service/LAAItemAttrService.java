/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.attr.service;

import com.leosys.app.item.entity.LAAItemAttr;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author fanyouyong
 */
public interface LAAItemAttrService extends BaseService<LAAItemAttr>{
      public List<LAAItemAttr> queryAttrsByItemId(Long itemId);
}
