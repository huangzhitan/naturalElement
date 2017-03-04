/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.type.service;

import com.leosys.app.item.entity.LAAType;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 *
 * @author fanyouyong
 */
public interface LAATypeService extends BaseService<LAAType>{
     public List findAllTypes();
    /**
     * 找到所有父节点的菜单
     * @return 
     */
    public List finAllParentTypes();
    public List findAllChildTypesByPId(Long pid);
    
    
}
