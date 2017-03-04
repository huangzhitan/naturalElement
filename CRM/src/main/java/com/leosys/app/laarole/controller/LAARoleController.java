/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laarole.controller;

import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.app.laanavi.service.LAANaviService;
import java.util.List;
import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laarole.service.LAARoleService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.ui.PageArr;
import com.leosys.core.utils.JsonUtil;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
 * @author wenjie
 */
@Controller
@RequestMapping("/laaRole")
public class LAARoleController {

   
    static Logger log = Logger.getLogger(LAARoleController.class);

    @Autowired
    private LAARoleService laaRoleService;
    @Autowired
    private LAANaviService laaNaviService;

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn addRole(LAARole role,String naviIds) {
        
        if (role == null) {
            return new AjaxReturn(false);
        } else {
            if(StringUtils.isNotEmpty(naviIds)){
                List<LAANavi> list = new ArrayList();
                String[] ids= naviIds.split(",");
                for(int i=0;i<ids.length;i++){
                  Long id=Long.parseLong(ids[i]);
                  LAANavi navi = (LAANavi)laaNaviService.querySingleEntity(LAANavi.class, id);
                  list.add(navi);
               }
              role.setNavis(list);
            }
            return new AjaxReturn(laaRoleService.add(role));
        }
    }

    /**
     * 得到单个角色完整信息
     *
     * @param roleid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSingleRole/{roleid}", method = RequestMethod.GET)
    public String getSingleRole(@PathVariable long roleid,Model model) throws Exception {
        LAARole role = (LAARole) laaRoleService.querySingleEntity(LAARole.class, roleid);
        model.addAttribute("role", role);
         List<LAANavi> navis = laaNaviService.finAllParentNavis();
        model.addAttribute("navis",navis );
        return "laarole/updateRole";
    }

    /**
     * 查询所有角色
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllRoles(Model model,@PathVariable int page) throws Exception {
        List<LAARole> roles = laaRoleService.findAllRoles();
        model.addAttribute("roles", roles);
        PageArr arr = new PageArr();
        arr.setCount(roles.size());
        arr.setPage(page);
        arr.setUrl("/getAll/");
        model.addAttribute("pagearr",arr);
        return "laarole/roleManager";
    }

    /**
     * 删除角色（伪删除）
     *
     * @param roleId
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/delRole/{roleId}", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxReturn delRole(@PathVariable Long roleId) throws Exception {
//        AjaxReturn ar = new AjaxReturn(laaRoleService.moveRoleToRecycle(roleId));
//        return ar;
//    }
    
    @RequestMapping(value = "/goAddRole",method = RequestMethod.GET)
    public String goAddRole(Model model) throws Exception{
        List<LAANavi> navis = laaNaviService.finAllParentNavis();
        model.addAttribute("navis",navis );
        return "laarole/addRole";
    }
    /**
     * 更新角色
     * @param role
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn updateRole(LAARole role,String naviIds) throws Exception{
        if(StringUtils.isNotEmpty(naviIds)){
                List<LAANavi> list = new ArrayList();
                String[] ids= naviIds.split(",");
                for(int i=0;i<ids.length;i++){
                  Long id=Long.parseLong(ids[i]);
                  LAANavi navi = (LAANavi)laaNaviService.querySingleEntity(LAANavi.class, id);
                  list.add(navi);
               }
               role.setNavis(list);
            }
        AjaxReturn ar = new AjaxReturn(laaRoleService.update(role));
        return ar;
    }
    

}
