/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.type.controller;

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
@RequestMapping("/laaType")
public class LAATypeController {
    @Autowired
    LAATypeService  laaTypeService;
     @RequestMapping(value = "/addType", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn addType(LAAType type) {
        if (type == null) {
            return new AjaxReturn(false);
        } else {
            return new AjaxReturn(laaTypeService.add(type));
        }
    }

    /**
     * 得到某个菜单的具体信息
     *
     * @param naviid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSingleType/{typeid}", method = RequestMethod.GET)
    public String getSingleType(@PathVariable long typeid,Model model) throws Exception {
        LAAType navi = (LAAType) laaTypeService.querySingleEntity(LAAType.class, typeid);
        List<LAAType> list = laaTypeService.finAllParentTypes();
        model.addAttribute("parentTypes", list);
        model.addAttribute("type", navi);
        return "type/updateType";
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
        List<LAAType> types = laaTypeService.findAllTypes();
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
        return "type/typeManager";
    }

    /**
     * 删除菜单
     *
     * @param naviid
     * @param session
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delType/{typeid}", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn delType(@PathVariable Long typeid,HttpSession session) throws Exception {
         AjaxReturn ar = new AjaxReturn(true);
         LAAType navi = (LAAType) laaTypeService.querySingleEntity(LAAType.class, typeid);
         if(navi.getIsParent()==1){
         List<LAAType> childs = laaTypeService.findAllChildTypesByPId(typeid);
         if(childs!=null&&childs.size()>0){
         ar.setStatus(false);
         ar.setContent("一级分类下面还有二级分类不可删除！！");
        
         }else{
           navi.setIsDel((byte)1);
          ar.setStatus(laaTypeService.update(navi));
         
         }
          return ar;
         }else{
         navi.setIsDel((byte)1);
          ar.setStatus(laaTypeService.update(navi));
           return ar;
         }
      

    }

    /**
     * 更新菜单
     *
     * @param navi
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateType", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn updateType(LAAType type) throws Exception {
        AjaxReturn ar = new AjaxReturn(laaTypeService.update(type));
        return ar;
    }
    /**
     * 开始添加菜单
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/goAddType",method = RequestMethod.GET)
    public String goAddType(Model model) throws Exception{
        List<LAAType> list = laaTypeService.finAllParentTypes();
        model.addAttribute("parentTypes", list);
        return "type/addType";
    }
}
