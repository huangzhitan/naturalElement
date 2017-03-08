/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.comment.controller;


import com.leosys.app.item.entity.LAAItem;
import com.leosys.app.item.entity.LAAItemFavo;
import com.leosys.app.item.entity.LAAItemImg;
import com.leosys.app.item.entity.LAAOrder;
import com.leosys.app.item.favo.service.LAAItemFavoService;
import com.leosys.app.item.img.service.LAAItemImgService;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.app.order.service.LAAOrderService;
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
      @Autowired
    LAAOrderService laaOrderService;
    
    /**
     * 我的收藏
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
    
     @RequestMapping(value = "/queryMyOrder", method = RequestMethod.POST)
    @ResponseBody
    public PageAjax queryMyOrder(Integer userId ,Integer page,Integer pageSize,Integer status,String itemName,Integer isCancel){
    String sql ="select t.*,t1.itemname,t1.pubimg,t1.fprice,t1.sprice,t1.tprice from leosys_order t join leosys_item t1 on(t.itemid= t1.itemid) where t.iscancel=0";
    if(status!=null){
    sql+=" and t.status="+status;
    }
    if(isCancel!=null){
    sql+=" and t.iscancel="+isCancel;
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
     /**
      * 添加订单
      * @param itemId 产品id
      * @param userId 用户id
      * @param payNum 购买数量
      * @param payPrice 价格
      * @return 
      */
      @RequestMapping(value = "/saveOrder", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn saveOrder(Long itemId,Long userId,Integer payNum,Double payPrice){
         AjaxReturn ar =new AjaxReturn();
         LAAOrder order = new LAAOrder();
         order.setItemId(itemId);
         order.setUserId(userId);
         order.setPayNum(payNum);
         order.setPayPrice(payPrice);
         order.setActiveTime(new Date());
         order.setOrderNo("smsj"+ new Date().getTime()+userId);
         boolean isSuccess=laaOrderService.add(order);
         Map<String,Object> params =new HashMap();
         params.put("orderId", order.getOrderId());
         ar.setStatus(isSuccess);
         ar.setParams(params);
         return ar;
   
    }
     /**
      * 我的消息接口
      * @param userId用户id
      * @param page
      * @param pageSize
      * @param messType消息类别  0 系统消息 1取消订单消息
      * @return 
      */
      @RequestMapping(value = "/queryMyMess", method = RequestMethod.POST)
    @ResponseBody
    public PageAjax queryMyMess(Integer userId ,Integer page,Integer pageSize,Integer messType){
    String sql ="select t.* from leosys_mess t where t.reciverid= "+userId+" and t.messtype=0";
    if(messType==1){
    sql="select t2.content,t.*,t1.itemname,t1.pubimg,t1.fprice,t1.sprice,t1.tprice from leosys_order t join leosys_item t1 on(t.itemid= t1.itemid) join leosys_mess t2 on(t.orderid=t2.orderid) where  t2.messtype=1 and t2.reciverid="+userId;
    }
   
     sql+=" order by t2.createtime desc";
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
     
}
