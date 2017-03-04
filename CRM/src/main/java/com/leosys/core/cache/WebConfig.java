/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.cache;

/**
 *
 * @author sam.zheng <zcl1866@sina.com>
 * @version <v0.1>
 * @date 2013-05-23 04:10:25
 * @copyright <sixboy>
 *
 * 平台参数配置类
 */
public class WebConfig {

    private String   freemarkTpl;
    private String   userFormFreemarkTpl;
    private String[] noLoginFilterFile; //不登录验证的文件
    private String[] validateApp; // 验证登陆app
    private AppStyleInfo appStyleInfo = new AppStyleInfo(); //APP样式风格
    
    private Ueditor ueditor = new Ueditor();

    public String[] getNoLoginFilterFile() {
        return noLoginFilterFile;
    }

    public void setNoLoginFilterFile(String[] noLoginFilterFile) {
        this.noLoginFilterFile = noLoginFilterFile;
    }

    public String[] getValidateApp() {
        return validateApp;
    }

    public void setValidateApp(String[] validateApp) {
        this.validateApp = validateApp;
    }

    public AppStyleInfo getAppStyleInfo() {
        return appStyleInfo;
    }

    public void setAppStyleInfo(AppStyleInfo appStyleInfo) {
        this.appStyleInfo = appStyleInfo;
    }

    public Ueditor getUeditor() {
        return ueditor;
    }

    public void setUeditor(Ueditor ueditor) {
        this.ueditor = ueditor;
    }

    public String getFreemarkTpl() {
        return freemarkTpl;
    }

    public void setFreemarkTpl(String freemarkTpl) {
        this.freemarkTpl = freemarkTpl;
    }

    public String getUserFormFreemarkTpl() {
        return userFormFreemarkTpl;
    }

    public void setUserFormFreemarkTpl(String userFormFreemarkTpl) {
        this.userFormFreemarkTpl = userFormFreemarkTpl;
    }
    
    
    
    
    
    

    public class AppStyleInfo {

        String appStyle;
        String include;
        String referform;
        String uiform;


        public String getAppStyle() {
            return appStyle;
        }

        public void setAppStyle(String appStyle) {
            this.appStyle = appStyle;
        }

        public String getInclude() {
            return include;
        }

        public void setInclude(String include) {
            this.include = include;
        }

        public String getReferform() {
            return referform;
        }

        public void setReferform(String referform) {
            this.referform = referform;
        }

        public String getUiform() {
            return uiform;
        }

        public void setUiform(String uiform) {
            this.uiform = uiform;
        }

        
        
    }
    
    public class Ueditor{
        
        private String filePath ;
        private String imagePath ;
        private String imageType[];
        private String fileType[];
        
        private int imageMaxSize;
        private int fileMaxSize;

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }

       

        public String[] getImageType() {
            return imageType;
        }

        public void setImageType(String[] imageType) {
            this.imageType = imageType;
        }

        public String[] getFileType() {
            return fileType;
        }

        public void setFileType(String[] fileType) {
            this.fileType = fileType;
        }

        public int getImageMaxSize() {
            return imageMaxSize;
        }

        public void setImageMaxSize(int imageMaxSize) {
            this.imageMaxSize = imageMaxSize;
        }

        public int getFileMaxSize() {
            return fileMaxSize;
        }

        public void setFileMaxSize(int fileMaxSize) {
            this.fileMaxSize = fileMaxSize;
        }
        
        
        
        
    }
}
