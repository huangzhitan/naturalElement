/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.controller;

import com.leosys.app.attr.service.LAAAttrService;
import com.leosys.app.item.attr.service.LAAItemAttrService;
import com.leosys.app.item.entity.LAAAttr;
import com.leosys.app.item.entity.LAAItem;
import com.leosys.app.item.entity.LAAItemAttr;
import com.leosys.app.item.entity.LAAItemImg;
import com.leosys.app.item.entity.LAAMess;
import com.leosys.app.item.entity.LAAOrder;
import com.leosys.app.item.entity.LAAType;
import com.leosys.app.item.img.service.LAAItemImgService;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.app.laauser.service.LAAUserService;
import com.leosys.app.mess.service.LAAMessService;
import com.leosys.app.order.service.LAAOrderService;
import com.leosys.app.type.service.LAATypeService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.ui.PageArr;
import com.leosys.core.utils.PageAjax;
import com.leosys.core.utils.PageList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author fanyouyong
 */
@Controller
@RequestMapping("/laaItem")
public class LAAItemController {
    @Autowired
    LAAItemService laaItemService;
    @Autowired
    LAATypeService  laaTypeService;
    @Autowired
    LAAItemImgService laaItemImgService;
     @Autowired
    LAAItemAttrService laaItemAttrService;
       @Autowired
    LAAAttrService laaAttrService;
        @Autowired
    LAAOrderService laaOrderService;
        @Autowired
    private LAAUserService laaUserService;
         @Autowired
    private LAAMessService laaMessService;
        @Autowired
        JdbcTemplate jdbcTemplate;
     @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllNavis(Model model,@PathVariable int page) throws Exception {
       
        return "item/index";
    }
     /**查询我的资源
     * 
     * @param page
     * @param pageSize
     * @param status
     * @param itemName
     * @param types
     * order 排序参数
     * @return  分页的信息   产品中的fprice ，sprice tprice 分别对应一级二级三级代理的不同价格   要根据代理等级   level 去区别展示
     */
    @RequestMapping(value = "/queryMyItem", method = RequestMethod.POST)
    @ResponseBody
    public PageAjax queryMyItem(Integer page,Integer pageSize,Integer status,Integer isDel,String itemName,Integer sprice,Integer eprice ){
    String sql ="select t.* from leosys_item t   where 1=1 ";
    if(isDel!=null){
    sql+=" and t.isdel="+isDel;
    }
    if(status!=null){
    sql+=" and t.status="+status;
    }
    
     if(itemName!=null&&!"".equals(itemName)){
     sql+=" and t.itemname like '%"+itemName+"%'";
     }
   
      
      if(sprice!=null){
    sql+=" and t.sprice>="+sprice;
    }
      if(eprice!=null){
    sql+=" and t.sprice<="+eprice;
    }
  
     
     
   
     sql+=" order by t.createtime desc ";
   
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
     @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public String getAllOrders(Model model) throws Exception {
        return "item/orderIndex";
    }
     @RequestMapping(value = "/getUpdateOrders", method = RequestMethod.GET)
    public String getUpdateOrders(Model model,Long orderId) throws Exception {
         LAAOrder order = laaOrderService.querySingleEntity(LAAOrder.class, orderId);
         model.addAttribute("order", order);
        return "item/updateOrder";
    }
    
     @RequestMapping(value = "/addItems", method = RequestMethod.GET)
    public String addItems(Model model) throws Exception {
        List<LAAType> types =laaTypeService.findAllTypes();
        List<LAAAttr> attrs =laaAttrService.findAllAttrs();
        model.addAttribute("types", types);
          model.addAttribute("attrs", attrs);
        return "item/additem";
    }
     @RequestMapping(value = "/saveItems", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn saveItems(LAAItem item) throws Exception {
       AjaxReturn ar = new AjaxReturn();
       Map<String,Object> map = new HashMap();
       ar.setStatus(laaItemService.add(item));
       
     
       map.put("itemId", item.getItemId());
        ar.setParams(map);
        return ar;
    }
    
     @RequestMapping(value = "/delList", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn delList( String ids){
    String[] idArr=ids.split(",");
     for(String idString:idArr){
         LAAItem item =laaItemService.querySingleEntity(LAAItem.class, Long.parseLong(idString));
         item.setIsDel((byte)1);
         laaItemService.update(item);
     }   
 
   
   return new AjaxReturn(true);
   
    }
    
       @RequestMapping(value = "/upList", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn upList( String ids){
    String[] idArr=ids.split(",");
     for(String idString:idArr){
         LAAItem item =laaItemService.querySingleEntity(LAAItem.class, Long.parseLong(idString));
         item.setIsDel((byte)0);
         laaItemService.update(item);
     }   
 
   
   return new AjaxReturn(true);
   
    }
    
      @RequestMapping(value = "/saveItemAttrs", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn saveItemAttrs(@RequestBody List<LAAItemAttr> attrs) throws Exception {
       AjaxReturn ar = new AjaxReturn();
       Map<String,Object> map = new HashMap();
       ar.setStatus(true);
       
       if(attrs!=null&&attrs.size()>0){
       for(LAAItemAttr attr:attrs){
      
       laaItemAttrService.add(attr);
       }
       }
 
     
        return ar;
    }
    
      @RequestMapping(value = "/saveImgs", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn saveImgs(String  urls,Long itemId)  {
        AjaxReturn ar = new AjaxReturn(true);
      String[] arrs= urls.split(",");
      try{
      for(String arr:arrs){
      LAAItemImg img = new LAAItemImg();
      img.setUrl(arr);
      img.setItemId(itemId);
      laaItemImgService.add(img);
      }
      }catch(Exception e){
      e.printStackTrace();
      ar.setStatus(false);
      }
        return ar;
    }
    
      @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String detail( Model model,Long itemId){
    Map<String,Object> result= new HashMap();    
    LAAItem item = (LAAItem)laaItemService.querySingleEntity(LAAItem.class, itemId);
    List<LAAItemImg> imgs=laaItemImgService.queryImgsByItemId(itemId);
    List<LAAType> types =laaTypeService.findAllTypes();
    List<LAAItemAttr> attrs =laaItemAttrService.queryAttrsByItemId(itemId);
        model.addAttribute("types", types);
        model.addAttribute("attrs", attrs);
   model.addAttribute("item", item);
   model.addAttribute("imgs", imgs);
    return "item/detailItem";
    }
       @RequestMapping(value = "/updateItems", method = RequestMethod.POST)
     @ResponseBody
    public AjaxReturn updateItems(LAAItem item) throws Exception {
       AjaxReturn ar = new AjaxReturn();
       Map<String,Object> map = new HashMap();
       ar.setStatus(laaItemService.update(item));
       
        return ar;
    }
    
      @RequestMapping(value = "/imgs", method = RequestMethod.GET)
    public String imgs( Model model,Long itemId){
    Map<String,Object> result= new HashMap();    
   
    List<LAAItemImg> imgs=laaItemImgService.queryImgsByItemId(itemId);
   
     
   model.addAttribute("itemId", itemId);
   model.addAttribute("imgs", imgs);
    return "item/imgs";
    }
      @RequestMapping(value = "/delImg", method = RequestMethod.POST)
      @ResponseBody
    public AjaxReturn delImg( Long id){
    LAAItemImg img =laaItemImgService.querySingleEntity(LAAItemImg.class, id);
   
   return new AjaxReturn(laaItemImgService.delete(img));
   
    }
    
      @RequestMapping(value = "/addImg", method = RequestMethod.GET)
    public String addImg( Model model,Long itemId){
    Map<String,Object> result= new HashMap();    
   
  
   
     
   model.addAttribute("itemId", itemId);

    return "item/addImg";
    }
    
      @RequestMapping(value = "/myMess", method = RequestMethod.GET)
    public String myMess( Model model){
     
   
  
   
     
   

    return "item/myMess";
    }
    
      @RequestMapping(value = "/toSendMess", method = RequestMethod.GET)
    public String toSendMess( Model model){

    return "item/sendMess";
    }
      @RequestMapping(value = "/queryMess", method = RequestMethod.GET)
    @ResponseBody
    public PageAjax queryMyMess(Integer userId ,Integer page,Integer pageSize,Integer isRead){
    String sql ="select t2.* from leosys_mess t2 where t2.reciverid= "+userId+" and t2.messtype=0 and t2.isdel=0";
   if(isRead!=null){
       sql+=" and t2.isread="+isRead;
   }
   
     sql+=" order by t2.createtime desc";
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    
      @RequestMapping(value = "/saveMyMess", method = RequestMethod.POST)
      @ResponseBody
    public AjaxReturn saveMyMess( Long reciverId,Long sendId,String messTitle,String content){
        AjaxReturn ar = new AjaxReturn(false);
        try{
if(reciverId==-1){
List<LAAUser> users = laaUserService.findAllLaaUsers();
for(LAAUser user:users){
     LAAMess mess  = new LAAMess();
         mess.setContent(content);
         mess.setMessTitle("系统消息："+messTitle);
         mess.setSenderId(-1l);
         mess.setReciverId(user.getuId());
         mess.setMessType((byte)0);
         laaMessService.add(mess);
}
ar.setStatus(true);
}else{
     LAAMess mess  = new LAAMess();
         mess.setContent(content);
         mess.setMessTitle("系统消息："+messTitle);
         mess.setSenderId(-1l);
         mess.setReciverId(reciverId);
         mess.setMessType((byte)0);
         laaMessService.add(mess);
}
        }catch(Exception e){
        return ar;
        }
    return ar;
    }
    
}
