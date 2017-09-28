/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.comment.controller;


import com.leosys.app.item.attr.service.LAAItemAttrService;
import com.leosys.app.item.entity.LAAItem;
import com.leosys.app.item.entity.LAAItemAttr;
import com.leosys.app.item.entity.LAAItemFavo;
import com.leosys.app.item.entity.LAAItemImg;
import com.leosys.app.item.entity.LAAMess;
import com.leosys.app.item.entity.LAAOrder;
import com.leosys.app.item.favo.service.LAAItemFavoService;
import com.leosys.app.item.img.service.LAAItemImgService;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laarole.service.LAARoleService;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.app.laauser.service.LAAUserService;
import com.leosys.app.mess.service.LAAMessService;
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
    @Autowired
    private LAAMessService laaMessService;
    @Autowired
    private LAAItemAttrService laaItemAttrService;
    
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
     * @return  status;//0待支付，1待发，2，待收3已收，4支付失败，5退款
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
    
    
            
            
    @RequestMapping(value = "/updateOrder", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn updateOrder(Long orderId,Integer status,Double payPrice){
          AjaxReturn ar = new AjaxReturn(false);
        LAAOrder order = laaOrderService.querySingleEntity(LAAOrder.class, orderId);
        if(order==null)
            return ar;
        if(status!=null){
            order.setStatus((byte)status.intValue());
        }
        if(payPrice!=null){
            order.setPayPrice(payPrice);
        }
        boolean isSuccess = laaOrderService.update(order);
         ar.setStatus(isSuccess);
       
         return ar;
   
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
    @RequestMapping(value = "/queryMyItem", method = RequestMethod.GET)
    @ResponseBody
    public PageAjax queryMyItem(Integer page,Integer pageSize,Integer status,String itemName,String types,String order,Integer sprice,Integer eprice,Integer longs,Integer weidth,Integer height,String attrs ){
    String sql ="select distinct  t.* from leosys_item t left join leosys_item_attr t1 on(t1.itemid=t.itemid)  where 1=1 ";
    
    sql+=" and t.isdel=0";
 
    
     if(itemName!=null&&!"".equals(itemName)){
     sql+=" and t.itemname like '%"+itemName+"%'";
     }
      if(types!=null&&!"".equals(types)){
     sql+=" and t.types like '%"+types+"%'";
     }
      
      if(sprice!=null){
    sql+=" and t.sprice>="+sprice;
    }
      if(eprice!=null){
    sql+=" and t.sprice<="+eprice;
    }
      if(longs!=null&&longs!=0){
          if(longs!=150&&longs!=510)
    sql+=" and ABS(t.longs-"+longs+")<=15";
          else if(longs==150)
               sql+=" and (t.longs-"+longs+")<=0";
          else if(longs==510)
                sql+=" and (t.longs-"+longs+")>=0";
    }
       if(weidth!=null&&weidth!=0){
           if(weidth!=60&&weidth!=140)
    sql+=" and ABS(t.weidth-"+weidth+")<=10";
           else if(weidth==60)
              sql+=" and (t.weidth-"+weidth+")<=0";   
           else if(weidth==140)
              sql+=" and (t.weidth-"+weidth+")>=0";  
    }
        if(height!=null&&height!=0){
            if(height!=8&&height!=12)
    sql+=" and ABS(t.height-"+height+")<=1";
            else if(height==8)
                   sql+=" and (t.height-"+height+")<=0";
              else if(height==12)
                   sql+=" and (t.height-"+height+")>=0";
    }
     
        if(attrs!=null&&!"".equals(attrs)&&!"0".equals(attrs)){
     sql+=" "+attrs;
     }
     
     if(order!=null&&!"".equals(order)){
     sql+=" order by "+order;
     }else{
     sql+=" order by t.status ,t.createtime desc ";
     }
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    /**
     * 查看产品详情
     * itemid 产品id
     * userid  用户id
     * @return 产品中的fprice ，sprice tprice 分别对应一级二级三级代理的不同价格   要根据代理等级   level 去区别展示
     */
    @RequestMapping(value = "/queryItemDetailById", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryItemDetailById(Long itemId,Long userId){
    Map<String,Object> result= new HashMap();    
    LAAItem item = (LAAItem)laaItemService.querySingleEntity(LAAItem.class, itemId);
    List<LAAItemImg> imgs=laaItemImgService.queryImgsByItemId(itemId);
     List<LAAItemAttr> attrs =laaItemAttrService.queryAttrsByItemId(itemId);
     List<Map<String,Object>> typeList = jdbcTemplate.queryForList(" select t.* from leosys_type t where t.typeid in( "+item.getTypes()+")");
   String types ="--";
   if(typeList!=null&&typeList.size()>0){
     for(Map<String,Object> type : typeList){
         types+=type.get("typename").toString()+"--";
     }
   }
     Integer  isshou =0;
     Long favoId=-1l;
     if(userId!=null){
      List<Map<String,Object>> favos = jdbcTemplate.queryForList(" select t.* from leosys_item_favo t where t.isdel=0 and t.itemid= "+itemId+" and t.userid="+userId);
  if(favos!=null&&favos.size()>0){
  isshou=1;
  favoId =Long.parseLong(favos.get(0).get("favoid").toString()) ;
  }
     }
     
    result.put("item", item);
    result.put("imgs", imgs);
    result.put("attrs", attrs);
    result.put("types", types);
    result.put("isshou", isshou);
    result.put("favoId", favoId);
    return result;
    }
    /**
     * 
     * @param itemId
     * @return 
     */
       @RequestMapping(value = "/queryTypes", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryTypes(Long itemId){
     return jdbcTemplate.queryForList("select t.* from leosys_type t where t.isdel=0");
    }
    
    /**
     * 添加收藏
     * @param itemId产品id
     * @param userId用户id
     * @return params = favoId   收藏id
     */
     @RequestMapping(value = "/saveFavo", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn saveFavo(Long itemId,Long userId){
         LAAItemFavo favo = new LAAItemFavo();
         favo.setCreateTime(new Date());
         favo.setIsDel((byte)0);
         favo.setItemId(itemId);
         favo.setUserId(userId);
         boolean isSuccess = laaItemFavoService.add(favo);
         Map<String,Object> map = new HashMap<String,Object>();
         AjaxReturn ar = new AjaxReturn();
         ar.setStatus(isSuccess);
         map.put("favoId",favo.getFavoId() );
         ar.setParams(map);
         return ar;
   
    }
     /**
      * 删除收藏   
      * @param favoId 收藏id
      * @return 
      */
       @RequestMapping(value = "/delFavo", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn delFavo(Long favoId){
         LAAItemFavo favo = laaItemFavoService.querySingleEntity(LAAItemFavo.class, favoId);
         if(favo==null)
             return new AjaxReturn(false);
  
         return new AjaxReturn(laaItemFavoService.delete(favo));
   
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
         AjaxReturn ar =new AjaxReturn(false);
         LAAItem item = laaItemService.querySingleEntity(LAAItem.class, itemId);
         if(item==null||item.getStatus()==1)
             return ar;
         
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
         if(isSuccess){
         item.setStatus(1);
         laaItemService.update(item);
         }
         return ar;
   
    }
     /**
      * 根据id获取订单
      * 订单id不是单号
      * @return 返回订单
      */
    @RequestMapping(value = "/queryOrderById", method = RequestMethod.GET)
    @ResponseBody
     public LAAOrder queryOrderById(Long orderId){
       LAAOrder order = (LAAOrder)laaOrderService.querySingleEntity(LAAOrder.class, orderId);
       return order;
    }
     /**
      * 线下支付
      * @param orderId 订单id
      * 
      * @return 
      */
      @RequestMapping(value = "/payUnderLine", method = RequestMethod.GET)
    @ResponseBody
     public AjaxReturn payUnderLine(Long orderId){
         AjaxReturn ar  = new AjaxReturn(false);
       LAAOrder order = (LAAOrder)laaOrderService.querySingleEntity(LAAOrder.class, orderId);
       order.setPayTime(new Date());
       order.setPayType((byte)2);
       order.setStatus((byte)1);
       boolean issuccess = laaOrderService.update(order);
       ar.setStatus(issuccess);
       try{
       payOfter(order);
       }catch(Exception e){
       e.printStackTrace();
       }
       return ar;
    }
     /**
      * 支付前验证
      * @param order
      * @return
      * @throws Exception 
      */
       @RequestMapping(value = "/payBefore", method = RequestMethod.GET)
    @ResponseBody
      public AjaxReturn payBefore(Long  orderId,String address,String lxrName,String phoneNo,String liuYan) throws Exception{
           AjaxReturn ar  = new AjaxReturn(false);
          LAAOrder order = (LAAOrder)laaOrderService.querySingleEntity(LAAOrder.class, orderId);
          if(order==null){
              ar.setContent("订单不存在！！！");
              return ar;
          }
          
          if(order.getIsCancel()==1){
          ar.setContent("订单过期！！！");
              return ar;
          }
          LAAItem item = laaItemService.querySingleEntity(LAAItem.class, order.getItemId());
          if(item==null||item.getNums()<1){
           ar.setContent("产品售罄，联系卖家补货！！！");
              return ar;
          }
          order.setAddress(address);
          order.setPhoneNo(phoneNo);
          order.setLxrName(lxrName);
          order.setLiuYan(liuYan);
        ar.setStatus(laaOrderService.update(order));
        return ar;
     }
     /**
      * 支付完成后的动作
      * @param order 
      */
     private void payOfter(LAAOrder order) throws Exception{
         String zfqudao="线下支付";
         if(order.getPayType()==0)
             zfqudao="支付宝";
         if(order.getPayType()==1)
             zfqudao="微信";
         Long itemId = order.getItemId();
         LAAItem item = laaItemService.querySingleEntity(LAAItem.class, itemId);
         item.setIsDel((byte)1);
         laaItemService.update(item);
         String content="有一笔："+item.getItemName()+"的订单支付完成，支付渠道："+zfqudao+"请注意查收并及时发货";
        
         List<Map<String,Object>> users = jdbcTemplate.queryForList("select t.*  from  leosys_user t join leosys_user_leosys_role t1 on(t.uid = t1.LAAUser_uid) join leosys_role t2 on(t1.roles_roleid=t2.roleid) where t2.level=1");
         for(Map<String,Object> map:users){
          LAAMess mess  = new LAAMess();
         mess.setContent(content);
         mess.setMessTitle("系统信息，交易提醒");
         mess.setSenderId(-1l);
         mess.setReciverId(Long.parseLong(map.get("uid").toString()));
         mess.setMessType((byte)0);
         laaMessService.add(mess);
         }
     
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
    String sql ="select t2.* from leosys_mess t2 where t2.reciverid= "+userId+" and t2.messtype=0 and t2.isdel=0";
    if(messType==1){
    sql="select t2.content,t2.messid,t.*,t1.itemname,t1.pubimg,t1.fprice,t1.sprice,t1.tprice from leosys_order t join leosys_item t1 on(t.itemid= t1.itemid) join leosys_mess t2 on(t.orderid=t2.orderid) where  t2.messtype=1 and t2.isdel=0 and t2.reciverid="+userId;
    }
   
     sql+=" order by t2.createtime desc";
    PageList pagelist=new PageList(jdbcTemplate,sql, page, pageSize);
    return pagelist.$pageAjax;
    }
    /**
     * 
     * @param userId
     * @param page
     * @param pageSize
     * @param messType
     * @return 
     */
    @RequestMapping(value = "/queryCounts", method = RequestMethod.GET)
    @ResponseBody
     public Map<String,Object> queryCounts(Integer userId ){
   Map<String,Object> result=new HashMap();
   result.put("messCount", 0);
    result.put("favoCount", 0);
    List mess=jdbcTemplate.queryForList("select t.* from leosys_mess t where t.isread=0 and t.reciverid="+userId);
     List favos=jdbcTemplate.queryForList("select t.* from leosys_item_favo t where t.isdel=0 and t.userid="+userId);
     if(mess!=null)
         result.put("messCount", mess.size());
     if(favos!=null)
         result.put("favoCount", favos.size());
     return result;
    }
    /**
     * 标记成已读的信息
     * @param messId
     * @return 
     */
     @RequestMapping(value = "/isRead", method = RequestMethod.GET)
    @ResponseBody
    public AjaxReturn isRead(Long messId){
    LAAMess mess = (LAAMess)laaMessService.querySingleEntity(LAAMess.class, messId);
    mess.setIsRead((byte)1);
    return new AjaxReturn(laaMessService.update(mess));
    }
    
     @RequestMapping(value = "/delMess", method = RequestMethod.GET)
    @ResponseBody
    public AjaxReturn delMess(Long messId){
    LAAMess mess = (LAAMess)laaMessService.querySingleEntity(LAAMess.class, messId);
    mess.setIsDel((byte)1);
    return new AjaxReturn(laaMessService.update(mess));
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
    String yanCode = SendMessage.postMessage(phoneNo, "验证码："+realText);
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
    * 获取回填用户
    * @param userId
    * @return 
    */
     @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    @ResponseBody
   public Map<String,Object> getUserById(Long userId){
       List<Map<String,Object>> users = jdbcTemplate.queryForList(" select * from leosys_user where uid="+userId);
       Map<String,Object> user =users.get(0);
   // LAAUser userNew = laaUserService.querySingleEntity(LAAUser.class, userId);
    return user;
   }
   /**
    * 修改用户信息
    * @param email邮箱
    * @param realName真名
    * @param uName用户名
    * @param address地址
    * @param userId用户id必填
    * @return 
    */
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    @ResponseBody
   public AjaxReturn updateUser(String email,String realName,String uName,String address,Long userId){
      AjaxReturn ar = new AjaxReturn(false); 
       try{
     
       LAAUser userNew = laaUserService.querySingleEntity(LAAUser.class, userId);
       userNew.setEmail(email);
       userNew.setName(realName);
        userNew.setAddress(address);
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
    * @return 返回参数用户id，用户名，用户等级要存在本地,地址电话，真名
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
        params.put("address", user.getAddress());
        params.put("phoneNo", phoneNo);
         params.put("realName", user.getName());
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
        ar.setParams(params);
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
     /**
      * 回滚订单
      * @param messid 消息id
      * @param orderId 订单id
      * @return 
      */
      @RequestMapping(value = "/rollbackOrder", method = RequestMethod.GET)
     @ResponseBody
     public AjaxReturn rollbackOrder(Long messId ,Long orderId){
      AjaxReturn ar = new AjaxReturn(false); 
       Map<String,Object> params = new HashMap();
       try{
       LAAOrder order = laaOrderService.querySingleEntity(LAAOrder.class, orderId);
       LAAItem item =laaItemService.querySingleEntity(LAAItem.class, order.getItemId());
       if(item.getStatus()==1)
           return ar;
       order.setActiveTime(new Date());
       order.setIsCancel((byte)0);
       ar.setStatus(laaOrderService.update(order));
       LAAMess mess =laaMessService.querySingleEntity(LAAMess.class, messId);
       mess.setIsDel((byte)1);
       laaMessService.update(mess);
        return ar;
       }catch(Exception e){
       e.printStackTrace();
       ar.setContent("异常："+e.getMessage());
       return ar;
       }
   
   }
     /**
      * 
      * @return 
      */
     @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
     @ResponseBody
     public AjaxReturn cancelOrder(){
      AjaxReturn ar = new AjaxReturn(true); 
      List<LAAOrder> orders = laaOrderService.queryCancels();
      first: for(LAAOrder order:orders){
       try{
      
     LAAItem item  =laaItemService.querySingleEntity(LAAItem.class,order.getItemId());
     item.setStatus(0);
     laaItemService.update(item);
     
       order.setIsCancel((byte)1);
       laaOrderService.update(order);
     LAAMess mess  = new LAAMess();
         mess.setContent("");
         mess.setMessTitle("系统信息");
         mess.setSenderId(-1l);
         mess.setReciverId(order.getUserId());
         mess.setMessType((byte)1);
         mess.setOrderId(order.getOrderId());
         mess.setIsDel((byte)0);
         laaMessService.add(mess);
     
       }catch(Exception e){
           e.printStackTrace();
      continue first;
       
       }
       }
       
          return ar;
   
   }
     
     @RequestMapping(value = "/cancelOrderOnly", method = RequestMethod.POST)
     @ResponseBody
     public AjaxReturn cancelOrderOnly(Long orderId){
      AjaxReturn ar = new AjaxReturn(true); 
 
      LAAOrder order =laaOrderService.querySingleEntity(LAAOrder.class, orderId);
     LAAItem item  =laaItemService.querySingleEntity(LAAItem.class,order.getItemId());
     item.setStatus(0);
     laaItemService.update(item);
     
         order.setIsCancel((byte)1);
         laaOrderService.update(order);
         LAAMess mess  = new LAAMess();
         mess.setContent("");
         mess.setMessTitle("系统信息");
         mess.setSenderId(-1l);
         mess.setReciverId(order.getUserId());
         mess.setMessType((byte)1);
         mess.setOrderId(order.getOrderId());
         mess.setIsDel((byte)0);
         laaMessService.add(mess);
     
      
     
       
          return ar;
   
   }
     
}
