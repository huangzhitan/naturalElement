/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.leosys.core.cache;

import java.util.Map;


/**
 *
 * @author sam.zheng <zcl1866@sina.com> 
 * @version <v0.1>
 * @date  2013-05-23 03:15:12
 * @copyright <sixboy>
 */
public class Cache {
    
    private Cache(){
        
    }
    
    private static Cache cache;
    
    public static Cache getInstance(){
        if(cache == null){
            cache = new Cache();
        }
        return cache;
    }
    private WebConfig config; //web 服务器配置
    
    private Map<String,Object> cacheData; //缓存数据对象

    public WebConfig getConfig() {
        return config;
    }

    public void setConfig(WebConfig config) {
        this.config = config;
    }

    public Map<String, Object> getCacheData() {
        return cacheData;
    }

    public void setCacheData(Map<String, Object> cacheData) {
        this.cacheData = cacheData;
    }
    
    public void clear(){
        if(cache!=null){
            cache = null;
        }
    }
    
    
    
    
    
    
    
}
