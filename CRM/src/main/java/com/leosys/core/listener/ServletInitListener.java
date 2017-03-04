/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.leosys.core.listener;

import com.leosys.core.cache.Cache;
import com.leosys.core.cache.Property;
import com.leosys.core.cache.WebConfig;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
/**
 *
 * @author sam.zheng <zcl1866@sina.com> 
 * @version <v0.1>
 * @date  2013-05-23 03:18:23
 * @copyright <sixboy>
 */
public class ServletInitListener implements ServletContextListener {

    static Logger log = Logger.getLogger(ServletInitListener.class);
    private Cache cache;
    private ServletContext context;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
         this.cache = Cache.getInstance();
         initWebConfigToCache(sce);
         log.debug("LeoSys FrameWork Cache is Initialized");
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        cache.clear();
        log.debug("LeoSys Cache is Destroyed");
    }
    
    /**
     * 初始化服务器相关参数到缓存中
     * @param sce 
     */
    private void initWebConfigToCache(ServletContextEvent sce){
         try {
             Properties props = new Properties();
             this.context = sce.getServletContext();         
             props.load(context.getResourceAsStream(context.getInitParameter(Property.WEB_INIT_CONFIG))); 
             log.debug("LeoSys is loading init success !!!");
             WebConfig config = new WebConfig();
             String freemarkTpl = props.getProperty("freemark.root");
             String userFormPath = props.getProperty("freemark.userform");
             config.setFreemarkTpl(freemarkTpl);
             config.setUserFormFreemarkTpl(userFormPath);
             //初始化系统样式
             String appStyle = props.getProperty("appStyle");
             String includePath = props.getProperty("freemark.include");
             String referformPath = props.getProperty("freemark.referform");
             String uiformPath = props.getProperty("freemark.uiform");
             config.getAppStyleInfo().setAppStyle(appStyle);
             config.getAppStyleInfo().setInclude(includePath);
             config.getAppStyleInfo().setReferform(referformPath);
             config.getAppStyleInfo().setUiform(uiformPath);
             //初始化系统验证规则参数
             String[] filterFile = props.getProperty(Property.VALIDATE_LOGIN_PAGE).split("\\;");
             String[] validateApp = props.getProperty(Property.VALIDATE_APP).split("\\;");
             config.setNoLoginFilterFile(filterFile);
             config.setValidateApp(validateApp);
             //初始UEditor参数配置
             config.getUeditor().setFileMaxSize(Integer.parseInt(props.getProperty("ueditor.files.maxsize")));
             config.getUeditor().setFileType(props.getProperty("ueditor.files.type").split("\\;"));
             config.getUeditor().setImageMaxSize(Integer.parseInt(props.getProperty("ueditor.images.maxsize")));
             config.getUeditor().setImageType(props.getProperty("ueditor.images.type").split("\\;"));
             config.getUeditor().setImagePath(props.getProperty("ueditor.imagepath"));
             config.getUeditor().setFilePath(props.getProperty("ueditor.filepath"));
             cache.setConfig(config);
             log.debug("LeoSys CacheModule is is init success !!!");
        } catch (IOException ex) {
             log.error(ex.getMessage(),ex);
        }
    }

}
