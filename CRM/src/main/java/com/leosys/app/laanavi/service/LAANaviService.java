/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laanavi.service;

import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.core.service.BaseService;
import java.util.List;

/**
 * 系统导航菜单服务接口
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-05-11 18:48
 */
public interface LAANaviService extends BaseService<LAANavi>{
    public List findAllNavis();
    /**
     * 找到所有父节点的菜单
     * @return 
     */
    public List finAllParentNavis();
    /**
     * 查询所有子节点
     * @return 
     */
    public List findAllChildNavis();
    /**
     * 移入回收站
     * @param naviId
     * @param uId
     * @return 
     */
   // public boolean moveToRecycle(long naviId, long uId);
      public List findAllChildNavisByPId(Long pid);
      /**
     * 获取子菜单根据fuID
     * 
     * @param navis
     * @return 
     */
    public List queryClistByParentIds(List<LAANavi> navis);
}
