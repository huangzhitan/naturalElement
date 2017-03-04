/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.app.laanavi.controller;

import com.leosys.app.laanavi.entity.LAANavi;
import com.leosys.app.laanavi.service.LAANaviService;
import com.leosys.app.laarole.entity.LAARole;
import com.leosys.app.laauser.entity.LAAUser;
import com.leosys.app.laauser.service.LAAUserService;
import com.leosys.core.ajax.AjaxReturn;
import com.leosys.core.ui.PageArr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
 * @author wenjie
 */
@Controller
@RequestMapping("/laaNavi")
public class LAANaviController {

    @Autowired
    private LAANaviService laaNaviService;
    @Autowired
    private LAAUserService laaUserService;

    /**
     * 添加导航菜单
     *
     * @param navi
     * @return
     */
    @RequestMapping(value = "/addNavi", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn addNavi(LAANavi navi) {
        if (navi == null) {
            return new AjaxReturn(false);
        } else {
            return new AjaxReturn(laaNaviService.add(navi));
        }
    }

    /**
     * 得到某个菜单的具体信息
     *
     * @param naviid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getSingleNavi/{naviid}", method = RequestMethod.GET)
    public String getSingleNavi(@PathVariable long naviid,Model model) throws Exception {
        LAANavi navi = (LAANavi) laaNaviService.querySingleEntity(LAANavi.class, naviid);
        List<LAANavi> list = laaNaviService.finAllParentNavis();
        model.addAttribute("parentNavis", list);
        model.addAttribute("navi", navi);
        return "laanavi/updateNavi";
    }

    /**
     * 查询所有菜单
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getAll/{page}", method = RequestMethod.GET)
    public String getAllNavis(Model model,@PathVariable int page) throws Exception {
        List<LAANavi> navis = laaNaviService.findAllNavis();
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
        model.addAttribute("navis",arr.getPageList());
        return "laanavi/naviManager";
    }

    /**
     * 删除菜单
     *
     * @param naviid
     * @param session
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/delNavi/{naviid}", method = RequestMethod.POST)
//    @ResponseBody
//    public AjaxReturn delNavi(@PathVariable Long naviid,HttpSession session) throws Exception {
//        long uId = (long) session.getAttribute("laaUserUid");
//        AjaxReturn ar = new AjaxReturn(laaNaviService.moveToRecycle(naviid, uId));
//        return ar;
//
//    }

    /**
     * 更新菜单
     *
     * @param navi
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateNavi", method = RequestMethod.POST)
    @ResponseBody
    public AjaxReturn updateNavi(LAANavi navi) throws Exception {
        AjaxReturn ar = new AjaxReturn(laaNaviService.update(navi));
        return ar;
    }
    /**
     * 开始添加菜单
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/goAddNavi",method = RequestMethod.GET)
    public String goAddNavi(Model model) throws Exception{
        List<LAANavi> list = laaNaviService.finAllParentNavis();
        model.addAttribute("parentNavis", list);
        return "laanavi/addNavi";
    }
    @RequestMapping(value = "/getLeftMenus",method = RequestMethod.GET)
    @ResponseBody
    public AjaxReturn getLeftMenus(Model model,HttpSession session,HttpServletRequest request) throws Exception {
        List<LAANavi> pList= new ArrayList();
         List<LAANavi> cList =new ArrayList();
        LAAUser user= (LAAUser)laaUserService.querySingleEntity(LAAUser.class, Long.parseLong(session.getAttribute("laaUserUid").toString()));
        List<LAARole> roles =user.getRoles();
        if(roles!=null||roles.size()>0){
            for(LAARole role :roles){
            pList.addAll(role.getNavis());
            
            }
            cList=laaNaviService.queryClistByParentIds(pList);
            
//            for (int i = 0; i < cList.size(); i++) {
//                LAANavi navi = cList.get(i);
//                if(!LicenseUtils.hasPermission(navi.getNaviUrl(), request.getServletContext().getRealPath("/resource/license"))){
//                    navi.setNaviUrl("#");
//                }
//            }
        
        }
//        Integer roleLevel = Integer.parseInt(session.getAttribute("rolelevel").toString());
//        if(roleLevel==1){
//         LAANavi navip= laaNaviService.querySingleEntity(LAANavi.class, 51l);
//         pList.add(navip);
//         cList=laaNaviService.findAllChildNavisByPId(51l);
//        }
//        else{
//        pList = laaNaviService.finAllParentNavis();
//        cList = laaNaviService.findAllChildNavis();
//        }
        model.addAttribute("parentNavis", pList);
        model.addAttribute("childNavis", cList);
        HashMap<String, Object> params = new HashMap<>();
        params.put("parentNavis", pList);
        params.put("childNavis", cList);

        AjaxReturn ar = new AjaxReturn(true);
        ar.setParams(params);
        return ar;
    }
}
