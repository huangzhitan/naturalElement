/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laauser.controller;

import com.leosys.app.laauser.entity.LAAUser;

import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.plugs.JavaMail;
import com.leosys.core.utils.DESUtil;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author wenjie
 */
@Controller
@RequestMapping("/index")
public class LoginController {
    
    @RequestMapping(value = "")
    public String index(Model model)throws Exception{
        
        return "user/login";
    }
  
    @RequestMapping(value = "/agree")
    public String agree(Model model)throws Exception{
        return "user/registerAgree";
    }
    
  
    
   
   
    
    @RequestMapping(value = "/importimg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn importImg(@RequestParam() MultipartFile file, HttpServletRequest request) throws Exception {
        AjaxReturn ar = new AjaxReturn();  
        Date  now = new Date(); 
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");//可以方便地修改日期格式
        String dateStr = dateFormat.format( now );
        String filename = file.getOriginalFilename();
        String bName =dateStr+"_"+filename;
        String str = filename.substring(filename.lastIndexOf(".") + 1);
      
        if (file.isEmpty()) {
             ar.setStatus(false);
             ar.setContent("文件为空，导入失败！");
             return ar;
        }
        if (!"jpg".equalsIgnoreCase(str) && !"jpeg".equalsIgnoreCase(str) && !"img".equalsIgnoreCase(str) && !"png".equalsIgnoreCase(str) && !"gif".equalsIgnoreCase(str)) {
             ar.setStatus(false);
             ar.setContent("文件格式错误，只能为图片文件！");
             return ar;
        }
        if (!file.isEmpty()) {
           
            InputStream input = file.getInputStream();
            String tomcatPath = System.getProperty("user.dir").substring(0,System.getProperty("user.dir").length()-3);           
            String imgUrl = tomcatPath +"webapps"+ "\\upload\\"+bName;
            File newFile = new File(imgUrl);
            if (!newFile.exists()) {
                newFile.createNewFile();
            }
            file.transferTo(newFile);

        }
        ar.setStatus(true);
        HashMap<String,Object> p = new HashMap<String, Object>();
        p.put("shortname", filename);
        p.put("longname", bName);
        ar.setParams(p);
        ar.setContent("保存成功！");
        return ar;

    }
 
    
        
   @RequestMapping(value = "/verify")
    public String toVerify(){
        return "user/verifySupplier";
    }
    
//    @RequestMapping(value = "/sendMail", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxReturn sendMail(HttpServletRequest request,String email,HttpSession session) throws Exception {
//        AjaxReturn ar =new AjaxReturn();
//        //System.out.println("email"+email); 
//        JavaMail m =new JavaMail();
//        String code =m.randomStr(6);
//        session.setAttribute("supplierCode",code);
//        m.setHost("smtp.sina.com");
//        m.setAccount("laaserver", "LEOSYS!qazxsw@");
//        m.send("laaserver@sina.com", email, "来自LAA服务器的邮件",  "验证码："+code);
//        
//        return ar;
//    }
    
   
    
    @RequestMapping(value = "/toModifiedPWD", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn toModifiedPWD(HttpServletRequest request,String code,HttpSession session) throws Exception {
        AjaxReturn ar =new AjaxReturn();
        System.out.println("code："+code);
        String codeSession =(String) session.getAttribute("supplierCode");
        System.out.println("codeSession："+codeSession);
        if(code.equals(codeSession)){
            ar.setStatus(true);
        }
        else{
             ar.setStatus(false);
        }
        return ar;
    }
    
        
//    @RequestMapping(value = "/modeifiedPWD", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxReturn modeifiedPWD(HttpServletRequest request,HttpSession session,Long supid,String pwd,Long uid) throws Exception {
//        AjaxReturn ar =new AjaxReturn();
//        DESUtil des = new DESUtil("leosys.com.cn");
//        System.out.println(supid+" "+pwd+" "+uid);
//        String pwd_new = des.encryptStr(pwd);
//     
//        LAAUser user =(LAAUser) laaUser(LAAUser.class,uid);
//        user.setPass(pwd_new);
//        ar.setStatus(laaSupService.update(user));
//        return ar;
//    }
    
        
}
