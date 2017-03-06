/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.interceptor;

import com.leosys.core.cache.Cache;
import com.leosys.core.cache.Property;
import com.leosys.core.cache.WebConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author sam.zheng <zcl1866@sina.com>
 * @version <v0.1>
 * @date 2013-05-23 11:14:55
 * @copyright
 */
@Repository
public class SessionInterceptor extends HandlerInterceptorAdapter {

    static Logger log = Logger.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        log.debug(uri);
        Cache cache = Cache.getInstance();
        WebConfig config = cache.getConfig();
        String[] noFilters = null;
        String[] validatApp = null;
        if (config != null) {
            noFilters = cache.getConfig().getNoLoginFilterFile();
            validatApp = cache.getConfig().getValidateApp();
        }
        boolean beFilter = true;
        if (validatApp != null) {
            for (String v : validatApp) {
                if (uri.indexOf(v) != -1) {
                    for (String s : noFilters) {
                        if (uri.indexOf(s) != -1) {
                            beFilter = false;
                            break;
                        }
                    }
                    if (beFilter) {
                        Object obj = request.getSession().getAttribute(Property.SESSION_KEY);
                        if (null == obj) {
                            String path = uri.substring(0, uri.lastIndexOf("/" + v));
                            response.sendRedirect(path + "/" + v + "/login");
                        }
                    }
                }
            }
        }
        String loginUrl = "/CRM/app/";
        String checkCode = "getCheckCode";
         String comment = "comment";
        if (!uri.contains(loginUrl) && !uri.contains(checkCode) && !uri.contains("login")&&!uri.contains("comment")) {
            Object obj = request.getSession().getAttribute(Property.SESSION_KEY);
            if (null == obj) {
                response.sendRedirect(loginUrl);
            }

        }
        
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println(request.getContextPath());
        String uri = request.getRequestURI();
        String uriArr[] = uri.split("/");
        String appPath =  uriArr[0] + "/" + uriArr[1] +"/"+uriArr[2] + "/" + uriArr[3];
        System.out.println(appPath);
        if(uriArr.length > 4){
           request.setAttribute("URL",appPath+"/"+uriArr[4]) ;
        }else{
           request.setAttribute("URL",appPath);
        }
        request.setAttribute("PATH", request.getContextPath());
        request.setAttribute("APP_PATH", appPath);
        request.setAttribute("RESOURCE",request.getContextPath() + "/" + "resource/tpl");
        super.postHandle(request, response, handler, modelAndView); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
