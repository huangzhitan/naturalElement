/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.item.controller;

import com.leosys.app.item.entity.LAAItem;
import com.leosys.app.item.service.LAAItemService;
import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.core.ui.PageArr;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author fanyouyong
 */
@Controller
@RequestMapping("/laaItem")
public class LAAItemController {
    @Autowired
    LAAItemService laaItemService;
    
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
}
