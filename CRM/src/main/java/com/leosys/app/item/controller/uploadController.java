/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.controller;

import com.leosys.core.ajax.AjaxReturn;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author fanyouyong
 */
@Controller
@RequestMapping("/upload")
public class uploadController {
    @RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn uploadImg(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request){
       AjaxReturn ar = new AjaxReturn();
       Map<String,Object> params = new HashMap();
        System.out.println("开始");  
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
//        String fileName = new Date().getTime()+".jpg";  
        System.out.println(path);  
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        params.put("fileUrl", request.getContextPath()+"/upload/"+fileName);  
        ar.setStatus(true);
        ar.setParams(params);
  
        return ar;  
    }
    }
    

