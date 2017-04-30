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
import com.leosys.app.item.entity.LAAType;
import com.leosys.app.item.img.service.LAAItemImgService;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.app.type.service.LAATypeService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.ui.PageArr;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
     @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllNavis(Model model,@PathVariable int page) throws Exception {
        List<LAAItem> navis = laaItemService.findAllItems();
        PageArr arr = new PageArr();
        arr.setCount(navis.size());
       
        arr.setUrl("/getAll/");
        arr.setList(navis);
        int pageTotal = arr.getPageTotal();
        if(page>pageTotal){
        page=pageTotal;
        }
         arr.setPage(page);
        arr.setPageTotal(pageTotal);
        
        model.addAttribute("pagearr",arr);
        model.addAttribute("items",arr.getPageList());
        return "item/index";
    }
     @RequestMapping(value = "/getOrders", method = RequestMethod.GET)
    public String getAllOrders(Model model) throws Exception {
        return "item/orderIndex";
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
    
}
