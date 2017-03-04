/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.leosys.core.utils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.apache.log4j.Logger;

/**
 *
 * @author sam.zheng <zcl1866@sina.com> 
 * @version <v0.1>
 * @date  2013-05-27 10:46:51
 * @copyright <sixboy>
 */
public class StringUtils {
    
    static Logger log = Logger.getLogger(StringUtils.class);
    /**
     * 将字符串转换成UFT-8形式
     * @param str
     * @return 
     */
    public static String toUTF8Str(String str){
        return reEncoding(str, "UTF-8");
    }
    /**
     * 将字符串转换成指定编码形式
     * @param text 要转化的字符串
     * @param newEncoding 编码规则
     * @return 
     */
     public static String reEncoding(String text, String newEncoding) { 
          String rs = null;
                try {
                        rs = new String(text.getBytes(), newEncoding);
                } catch (UnsupportedEncodingException e) {
                        log.error("转化编码发生错误" + newEncoding);
                        throw new RuntimeException(e);
                }
                return rs; 
     }
     
     public static  String  getUUID(){
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().replaceAll("-", "");
        return uid;
     }
    
}
