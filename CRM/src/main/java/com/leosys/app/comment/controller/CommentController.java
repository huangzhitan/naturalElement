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
import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laarole.service.LAARoleService;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.app.laauser.service.LAAUserService;
import com.leosys.app.order.service.LAAOrderService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.utils.DESUtil;
import com.leosys.core.utils.PageAjax;
import com.leosys.core.utils.PageList;
import com.leosys.core.utils.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.leosys.core.utils.StringUtils;
import java.io.IOException;
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
       @Autowired
    private LAAUserService laaUserService;
    @Autowired
    private LAARoleService laaRoleService;
    
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
    
     @RequestMapping(value = "/queryMyOrder", method = RequestMethod.GET)
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
     * @return  分页的信息   产品中的fprice ，sprice tprice 分别对应一级二级三级代理的不同价格   要根据代理等级   level 去区别展示
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
     * @return 产品中的fprice ，sprice tprice 分别对应一级二级三级代理的不同价格   要根据代理等级   level 去区别展示
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
     * @param itemId产品id
     * @param userId用户id
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
      * @return 如果是订单消息 返回订单产品的产品中的fprice ，sprice tprice 分别对应一级二级三级代理的不同价格   要根据代理等级   level 去区别展示
      */
      @RequestMapping(value = "/queryMyMess", method = RequestMethod.GET)
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
    /**
     * 邀请好友
     * @param userId自己的id
     * @param phoneNo电话号码
     * @return 
     */
    @RequestMapping(value = "/yaoQing", method = RequestMethod.GET)
    @ResponseBody
    public AjaxReturn  yaoQing(Long userId ,String phoneNo) {
        
        
    AjaxReturn ar = new AjaxReturn(false);
    if(userId==null){
    return ar;
    }
    LAAUser user = laaUserService.querySingleEntity(LAAUser.class, userId);
    if(user==null){
    return ar;
    }
    LAARole role=user.getRoles().size()==0?null:user.getRoles().get(0);
    LAAUser userNew = laaUserService.getSingleUserByPhone(phoneNo)!=null?laaUserService.getSingleUserByPhone(phoneNo): new LAAUser();
    if(userNew.getIsYan()==1){
        ar.setContent("手机已存在！！！");
    return ar;
    }
    userNew.setEmail("");
    userNew.setName("");
    userNew.setPass("");
    userNew.setuName("");
    userNew.setPhoneNo(phoneNo);
    List<LAARole> roles = null;
    if(role!=null){
       int level = role.getLevel();
       if(level==3){
        roles= laaRoleService.findAllRolesByLevel(level);
       }else{
       roles= laaRoleService.findAllRolesByLevel(level+1);
       }
    }
    try{
    String text = (new Date().getTime()+"");
    
    String realText = text.substring(text.length()-6,text.length());
    String yanCode = SendMessage.postMessage(phoneNo, "邀请码："+realText);
    if(Integer.parseInt(yanCode)<0){
        System.out.println(yanCode);
    return ar;
    }else{
    userNew.setYanCode(realText);
    if(laaUserService.getSingleUserByPhone(phoneNo)!=null){
         ar.setStatus(laaUserService.update(userNew));}
    else{
        userNew.setRoles(roles);
    ar.setStatus(laaUserService.add(userNew));
    
    }
    return ar;
    }
    
    }catch(Exception e){
    e.printStackTrace();
    return ar;
    }
    
    }
    /**
     * 
     * @param phoneNo电话号码
     * @param yanCode验证码
     * @return 参数  新用户已经验证的userid
     */
     @RequestMapping(value = "/yanZheng", method = RequestMethod.GET)
    @ResponseBody
   public AjaxReturn yanZheng(String phoneNo,String yanCode){
   AjaxReturn ar  = new AjaxReturn(false);
   Map<String,Object> params = new HashMap();
   
   LAAUser user = laaUserService.getSingleUserByPhone(phoneNo);
   if(user==null){
       ar.setContent("请查看电话号码是否正确！！！");
       return ar;
       
   }
   if(!yanCode.equals(user.getYanCode())){
     ar.setContent("请查看电话号码是否正确！！！");
       return ar;
   }else{
       user.setIsYan(1);
       
   ar.setStatus(laaUserService.update(user));
   params.put("userId",user.getuId());
   ar.setParams(params);
   return ar;
   }
   }
   /**
    * 
    * @param email邮箱
    * @param realName真名
    * @param password 密码
    * @param uName用户名
    * @param userId用户id  
    * @return 
    */
    @RequestMapping(value = "/zhuche", method = RequestMethod.GET)
    @ResponseBody
   public AjaxReturn zhuCe(String email,String realName,String password,String uName,Long userId){
      AjaxReturn ar = new AjaxReturn(false); 
       try{
     
       LAAUser userNew = laaUserService.querySingleEntity(LAAUser.class, userId);
       userNew.setEmail(email);
       userNew.setName(realName);
        DESUtil des = new DESUtil("leosys.com.cn");
        String passAtferDes = des.encryptStr(password);
        userNew.setPass(passAtferDes);
        userNew.setuName(uName);
        ar.setStatus(laaUserService.update(userNew));
        return ar;
       }catch(Exception e){
       e.printStackTrace();
       return ar;
       }
   
   }
   /**
    * 
    * @param phoneNo
    * @param password
    * @return 返回参数用户id，用户名，用户等级要存在本地
    */
     @RequestMapping(value = "/loginComment", method = RequestMethod.GET)
    @ResponseBody
   public AjaxReturn loginComment(String phoneNo,String password){
      AjaxReturn ar = new AjaxReturn(false); 
       Map<String,Object> params = new HashMap();
       try{
     
        LAAUser user = laaUserService.getSingleUserByPhone(phoneNo);
        
        DESUtil des = new DESUtil("leosys.com.cn");
        String passAtferDes = des.encryptStr(password);
        if(user!=null&&user.getPass().equalsIgnoreCase(passAtferDes)&&user.getRoles()!=null&&user.getRoles().size()>0){
        LAARole role= user.getRoles().get(0);
        ar.setStatus(true);
        params.put("userId", user.getuId());
        params.put("level", role.getLevel());
        params.put("userName", user.getuName());
        ar.setParams(params);
        return ar;
        }
        
        
        return ar;
       }catch(Exception e){
       e.printStackTrace();
       ar.setContent("异常："+e.getMessage());
       return ar;
       }
   
   }
   /**
    * 获取验证码的接口
    * @param phoneNo
    * @return 返回回去的是验证吗returnCode 请在客户端直接验证正确错误
    */
     @RequestMapping(value = "/getMessCode", method = RequestMethod.GET)
     @ResponseBody
     public AjaxReturn getMessCode(String phoneNo){
      AjaxReturn ar = new AjaxReturn(false); 
       Map<String,Object> params = new HashMap();
       try{
     
        LAAUser user = laaUserService.getSingleUserByPhone(phoneNo);
        if(user==null){
        ar.setContent("此号码尚未注册，请先注册！！！");
        return ar;
        }
        String returnCode=user.getYanCode();
       String text = (new Date().getTime()+"");
    
       String realText = text.substring(text.length()-6,text.length());
       String yanCode = SendMessage.postMessage(phoneNo, "邀请码："+realText);
       if(Integer.parseInt(yanCode)>0){
       returnCode=realText;
       }
       user.setYanCode(returnCode);
       laaUserService.update(user);
       ar.setStatus(true);
       params.put("returnCode",returnCode);
        
        return ar;
       }catch(Exception e){
       e.printStackTrace();
       ar.setContent("异常："+e.getMessage());
       return ar;
       }
   
   }
     /**
      * 修改密码
      * @param phoneNo电话
      * @param password新密码
      * @return 
      */
      @RequestMapping(value = "/modPass", method = RequestMethod.GET)
     @ResponseBody
     public AjaxReturn modPass(String phoneNo,String password){
      AjaxReturn ar = new AjaxReturn(false); 
       Map<String,Object> params = new HashMap();
       try{
     
        LAAUser user = laaUserService.getSingleUserByPhone(phoneNo);
        DESUtil des = new DESUtil("leosys.com.cn");
        String passAtferDes = des.encryptStr(password);
        user.setPass(passAtferDes);
       ar.setStatus(laaUserService.update(user));
        
        return ar;
       }catch(Exception e){
       e.printStackTrace();
       ar.setContent("异常："+e.getMessage());
       return ar;
       }
   
   }
     
}
