/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.comment.controller;


import com.leosys.app.item.entity.LAAItem;
import com.leosys.app.item.entity.LAAItemFavo;
import com.leosys.app.item.entity.LAAItemImg;
import com.leosys.app.item.favo.service.LAAItemFavoService;
import com.leosys.app.item.img.service.LAAItemImgService;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.utils.PageAjax;
import com.leosys.core.utils.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.leosys.core.utils.StringUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fanyouyong
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @Autowired
    LAAItemService laaItemService;
     @Autowired
    LAAItemImgService laaItemImgService;
     @Autowired
     LAAItemFavoService laaItemFavoService;
    
    /**
     * 我的订单
     * @param userId用户id
     * @param page当前页数
     * @param pageSize页面大小
     * @return 
     */
    @RequestMapping(value = "/queryMyFavo", method = RequestMethod.GET)
    @ResponseBody
    public PageAjax queryMyFavo(Integer userId ,Integer page,Integer pageSize){
    String sql ="select t.*,t1.itemname,t1.pubimg,t1.fprice,t1.sprice,t1.tprice from leosys_item_favo t join leosys_item t1 on(t.itemid= t1.itemid) where t.isdel=0 and t.userid="+userId;
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    /**
     * 我的订单
     * @param userId用户id
     * @param page
     * @param pageSize
     * @param status状态
     * @param itemName产品名称
     * @return 
     */
    
     @RequestMapping(value = "/queryMyOrder", method = RequestMethod.GET)
    @ResponseBody
    public PageAjax queryMyOrder(Integer userId ,Integer page,Integer pageSize,Integer status,String itemName){
    String sql ="select t.*,t1.itemname,t1.pubimg,t1.fprice,t1.sprice,t1.tprice from leosys_order t join leosys_item t1 on(t.itemid= t1.itemid) where t.iscancel=0";
    if(status!=null){
    sql+=" and t.status="+status;
    }
     if(userId!=null){
    sql+=" and t.userid="+userId;
    }
     if(itemName!=null&&!"".equals(itemName)){
     sql+=" and t1.itemname like '%"+itemName+"%'";
     }
     sql+=" order by t.activetime desc";
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    
    /**查询我的资源
     * 
     * @param page
     * @param pageSize
     * @param status
     * @param itemName
     * @param types
     * @return 
     */
    @RequestMapping(value = "/queryMyItem", method = RequestMethod.GET)
    @ResponseBody
    public PageAjax queryMyItem(Integer page,Integer pageSize,Integer status,String itemName,String types){
    String sql ="select t.* from leosys_item t where 1=1 ";
    if(status!=null){
    sql+=" and t.status="+status;
    }
    
     if(itemName!=null&&!"".equals(itemName)){
     sql+=" and t1.itemname like '%"+itemName+"%'";
     }
      if(types!=null&&!"".equals(types)){
     sql+=" and t1.types like '%"+types+"%'";
     }
     sql+=" order by t.activetime desc";
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    /**
     * 查看产品详情
     * @return 
     */
    @RequestMapping(value = "/queryItemDetailById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryItemDetailById(Long itemId){
    Map<String,Object> result= new HashMap();    
    LAAItem item = (LAAItem)laaItemService.querySingleEntity(LAAItem.class, itemId);
    List<LAAItemImg> imgs=laaItemImgService.queryImgsByItemId(itemId);
    result.put("item", item);
    result.put("imgs", imgs);
    return result;
    }
    /**
     * 添加收藏
     * @param itemId
     * @param userId
     * @return 
     */
     @RequestMapping(value = "/saveFavo", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn saveFavo(Long itemId,Long userId){
         LAAItemFavo favo = new LAAItemFavo();
         favo.setCreateTime(new Date());
         favo.setIsDel((byte)0);
         favo.setItemId(itemId);
         favo.setUserId(userId);
         return new AjaxReturn(laaItemFavoService.add(favo));
   
    }
}
