/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.attr.controller;

import com.leosys.app.attr.service.LAAAttrService;
import com.leosys.app.item.entity.LAAAttr;
import com.leosys.app.item.entity.LAAType;
import com.leosys.app.type.service.LAATypeService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.ui.PageArr;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author fanyouyong
 */
@Controller
@RequestMapping("/laaAttr")
public class LAAAttrController {
    
    
    @Autowired
    LAAAttrService  laaAttrService;
    @Autowired
   LAATypeService laaTypeService;
     @RequestMapping(value = "/addAttr", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn addType(LAAAttr type) {
        if (type == null) {
            return new AjaxReturn(false);
        } else {
            return new AjaxReturn(laaAttrService.add(type));
        }
    }

    /**
     * 得到某个菜单的具体信息
     *
     * @param naviid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSingleAttr/{typeid}", method = RequestMethod.GET)
    public String getSingleType(@PathVariable long typeid,Model model) throws Exception {
        LAAAttr navi = (LAAAttr) laaAttrService.querySingleEntity(LAAAttr.class, typeid);
        List<LAAType> list = laaTypeService.finAllParentTypes();
        model.addAttribute("parentNavis", list);
        model.addAttribute("navi", navi);
        return "attr/updateAttr";
    }

    /**
     * 查询所有菜单
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllTypes(Model model,@PathVariable int page) throws Exception {
        List<LAAType> types = laaAttrService.findAllAttrs();
        PageArr arr = new PageArr();
        arr.setCount(types.size());
       
        arr.setUrl("/getAll/");
        arr.setList(types);
        int pageTotal = arr.getPageTotal();
        if(page>pageTotal){
        page=pageTotal;
        }
         arr.setPage(page);
        arr.setPageTotal(pageTotal);
        
        model.addAttribute("pagearr",arr);
        model.addAttribute("types",arr.getPageList());
        return "attr/attrMannger";
    }

    /**
     * 删除菜单
     *
     * @param naviid
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delAttr/{typeid}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn delType(@PathVariable Long typeid,HttpSession session) throws Exception {
         AjaxReturn ar = new AjaxReturn(true);
         LAAAttr navi = (LAAAttr) laaAttrService.querySingleEntity(LAAAttr.class, typeid);
        
         navi.setIsDel((byte)1);
          ar.setStatus(laaAttrService.update(navi));
           return ar;
        
      

    }

    /**
     * 更新菜单
     *
     * @param navi
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateAttr", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn updateType(LAAAttr type) throws Exception {
        AjaxReturn ar = new AjaxReturn(laaAttrService.update(type));
        return ar;
    }
    /**
     * 开始添加菜单
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/goAddAttr",method = RequestMethod.GET)
    public String goAddType(Model model) throws Exception{
        List<LAAType> list = laaTypeService.finAllParentTypes();
        model.addAttribute("parentTypes", list);
        return "attr/addAttr";
    }
    
}
