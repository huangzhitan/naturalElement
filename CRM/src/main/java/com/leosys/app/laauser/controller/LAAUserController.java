/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laauser.controller;

import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laarole.service.LAARoleService;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.app.laauser.service.LAAUserService;


import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.common.QueryType;
import com.leosys.core.plugs.JavaMail;
import com.leosys.core.ui.PageArr;
import com.leosys.core.utils.DESUtil;
import com.leosys.core.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import org.apache.log4j.Logger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wenjie
 * @Copyright Copyright (C) leosys http://www.leosys.com
 * @date 2015-5-8 10:22
 */
@Controller
@RequestMapping("/laaUser")
public class LAAUserController {
 static Logger log = Logger.getLogger(LAAUserController.class);

    @Autowired
    private LAAUserService laaUserService;
    @Autowired
    private LAARoleService laaRoleService;
   

    /**
     * 查询所有用户 
     *
     * @param model
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllUsers(Model model,@PathVariable int page) throws Exception {
        List<LAAUser> list = laaUserService.findAllLaaUsers();
//        model.addAttribute("users", list);
        PageArr arr = new PageArr();
        arr.setCount(list.size());
        arr.setPage(page);
        arr.setUrl("/getAll/");
        arr.setList(list);
        int pageTotal = arr.getPageTotal();
        arr.setPageTotal(pageTotal);
        
        model.addAttribute("pagearr",arr);
        model.addAttribute("users", arr.getPageList());
        //TODO 待添加页面路径  
        return "user/userManager";
    }
    /**
     * 打开添加用户页面
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/goAddUser",method = RequestMethod.GET)
    public String goAddUser(Model model) throws Exception{
        List<LAARole> roles= laaRoleService.findAllRoles();
        model.addAttribute("roles", roles); 
        return "user/addUser";
    
    }

    /**
     * 删除用户（伪删除）
     *
     * @param uid 用户ID
     * @return
     * @throws java.lang.Exception
     */
//    @RequestMapping(value = "/delUser/{uid}", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxReturn deleteUser(@PathVariable long uid,HttpSession session) throws Exception {
//        long editUid = (long) session.getAttribute("laaUserUid");
//        AjaxReturn ar = new AjaxReturn(laaUserService.moveToRecycle(uid, editUid));
//        return ar;
//    }

    /**
     * 新增用户
     *
     * @param user 用户信息
     * @return
     * @throws java.lang.Exception
     */
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn saveUser(LAAUser user,Long roleId) throws Exception {
        DESUtil des = new DESUtil("leosys.com.cn");
        String pass = user.getPass();
        String passAtferDes = des.encryptStr(pass);
        user.setPass(passAtferDes);
        if(roleId!=null){
          List<LAARole> list = new ArrayList();
          LAARole role=laaRoleService.querySingleEntity(LAARole.class, roleId);
          list.add(role);
          user.setRoles(list);
        }
        AjaxReturn ar = new AjaxReturn(laaUserService.add(user));
        return ar;
    }

    /**
     * 得到用户完整信息（更新用户之前）
     *
     * @param uid 用户ID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSingleUser/{uid}", method = RequestMethod.GET)
    public String getSingleUser(@PathVariable long uid,Model model) throws Exception {
        LAAUser laaUser = (LAAUser) laaUserService.querySingleEntity(LAAUser.class, uid);
           List<LAARole> roles= laaRoleService.findAllRoles();
       List<LAARole> list= laaUser.getRoles();
       LAARole role=new LAARole() ;
       if(list!=null &&list.size()>0)
         role=list.get(0);
       model.addAttribute("role", role);
        model.addAttribute("user", laaUser);
        model.addAttribute("roles", roles);
        return "user/updateUser";   
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn updateUser(LAAUser user,Long roleId) throws Exception {
        LAAUser laaUser = (LAAUser) laaUserService.querySingleEntity(LAAUser.class, user.getuId());
        laaUser.setEmail(user.getEmail());
        laaUser.setName(user.getName());
        laaUser.setSex(user.getSex());
        if(roleId !=null){
        LAARole role=laaRoleService.querySingleEntity(LAARole.class, roleId);
        List<LAARole> list = new ArrayList();
        list.add(role);
        laaUser.setRoles(list);
        }
        AjaxReturn ar = new AjaxReturn(laaUserService.update(laaUser));
        return ar;
    }
    /**
     * 登入
     * @param uname
     * @param pass
     * @param seccode
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public AjaxReturn  login(String uname,String pass,String seccode,HttpSession session)throws Exception{
        Date date = new Date();
        String loginTime=String.valueOf(date.getTime());
        HashMap<String,Object> map = new HashMap<String,Object>();
        laaUserService.clear();
        boolean isRightCheckCode = seccode.equalsIgnoreCase((String)session.getAttribute("laaCheckCode"));
        AjaxReturn ar =  new AjaxReturn(isRightCheckCode);
        if(isRightCheckCode){
            LAAUser user = laaUserService.getSingleUserByUname(uname);
            DESUtil des = new DESUtil("leosys.com.cn");
            String passAtferDes = des.encryptStr(pass);
            if (user != null && passAtferDes.equalsIgnoreCase(user.getPass())) {
               
                ar.setContent("getAll/1");
                session.setAttribute("laaUserName", user.getName());
                session.setAttribute("laaUserUid", user.getuId());
                session.setAttribute("loginTime", loginTime);
                
                map.put("laaUserName", user.getName());
                map.put("laaUserUid", user.getuId());
                map.put("loginTime", loginTime);
                
                //添加右下角提示框session标示
                session.setAttribute("auditTipsFlag",0);
                session.setAttribute("auditTipsCount",0);
                if(user.getRoles()!=null && user.getRoles().size()>0){
                session.setAttribute("rolelevel", user.getRoles().get(0).getLevel());
                 map.put("rolelevel", user.getRoles().get(0).getLevel());
                }else{
                session.setAttribute("rolelevel", 1);
                map.put("rolelevel",1);
                }
               ar.setParams(map);
            }else{
                ar.setStatus(false);
                ar.setContent("用户名密码错误");
            }
        }else{
            ar.setContent("验证码错误");
        }
        return ar;
    }
    /**
     * 登出
     * @param session
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public AjaxReturn logout(HttpSession session)throws Exception{
        session.removeAttribute("laaUserName");
        session.removeAttribute("laaUserUid");
        session.removeAttribute("loginTime");
        return new AjaxReturn(true);
    }
    
    /**
     * 打开修改密码界面
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/goChangePsw/{uId}",method = RequestMethod.GET)
    public String goChangePsw(@PathVariable long uId,Model model) throws Exception{
        model.addAttribute("uId", uId);
        return "user/changePsw";
    
    }
    /**
     * 修改用户密码
     * @param uId
     * @param oldPass
     * @param newPass
     * @return 
     */
    @RequestMapping(value = "/changePsw" ,method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn changePsw(long uId, String oldPass, String newPass) {
        AjaxReturn ar = new AjaxReturn(true);
        LAAUser user = laaUserService.querySingleEntity(LAAUser.class, uId);
        DESUtil des = new DESUtil("leosys.com.cn");
        String oldPassAtferDes = des.encryptStr(oldPass);
        if (oldPassAtferDes.equals(user.getPass())) {
            String newPassAfterDes = des.encryptStr(newPass);
            user.setPass(newPassAfterDes);
            laaUserService.update(user);
        } else {
            ar.setStatus(false);
            ar.setContent("原密码错误");
        }

        return ar;
    }
    
   
    @RequestMapping(value = "/homePage",method = RequestMethod.GET)
    public String toHomePage(){
    return "user/homePage";
    }
    
  
    
    
    //供应商注册审核
    @RequestMapping(value = "/registerManager",method = RequestMethod.GET)
    public String toRegister(){
        return "user/registerManager";
    }
    @RequestMapping(value = "/registerManager_success",method = RequestMethod.GET)
    public String toRegisterSuccess(){
        return "user/registerManager_success";
    }
     @RequestMapping(value = "/registerManager_fail",method = RequestMethod.GET)
    public String toRegisterFail(){
        return "user/registerManager_fail";
    }
   
    
    
   
    
    @RequestMapping(value="/setSessionParams/", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn setSessionParams(int flag,int count,HttpSession session){
        AjaxReturn ar =new AjaxReturn();
        session.setAttribute("auditTipsFlag",flag);
        session.setAttribute("auditTipsCount",count);
        return ar;
    }
    
}
