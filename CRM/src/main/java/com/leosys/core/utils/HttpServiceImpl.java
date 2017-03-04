/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author zhuangweicheng
 */
public class HttpServiceImpl {
    public static String getXmlPath()  
    {  
        //file:/D:/JavaWeb/.metadata/.me_tcat/webapps/TestBeanUtils/WEB-INF/classes/   
        String path=HttpServiceImpl.class.getResource("/").getPath();  
        path=path.substring(1, path.indexOf("classes"));
        path+="urlPro.properties";  
        //System.out.println(path);  
        return path;  
    }  
    
    public static String getMyitem(String letter,Integer page){
        Properties p = new Properties();
        String urls= getXmlPath();
        try{
         p.load(new FileInputStream(urls));
        }catch(Exception e){
        e.printStackTrace();
        }
        String subUrl=p.getProperty("path");
          String restUrl =subUrl+"model=myservice&action=getwebdomainsitebypage&page="+page+"&pagesize=10";
          if(StringUtils.isNotEmpty(letter)){
            restUrl+="&letter="+letter;  
          }
          StringBuffer strBuf;
          strBuf = new StringBuffer();  
                
          try{  
              URL url = new URL(restUrl);  
              HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
              conn.setRequestMethod("POST");
              BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));//转码。  
              String line = null;  
              while ((line = reader.readLine()) != null)  
                  strBuf.append(line + " ");  
                  reader.close();  
          }catch(MalformedURLException e) {  
              e.printStackTrace();   
          }catch(IOException e){  
              e.printStackTrace();   
          }     
          System.out.println(strBuf.toString());
          return strBuf.toString();  
      }
     public static String getZiku(String domin){
          Properties p = new Properties();
        String urls= getXmlPath();
        try{
         p.load(new FileInputStream(urls));
        }catch(Exception e){
        e.printStackTrace();
        }
        String subUrl=p.getProperty("path");
        String restUrl=subUrl+"model=myservice&action=getwebsiteziku&domain="+domin+"&pagesize=1000";
         StringBuffer strBuf;
          strBuf = new StringBuffer();  
                
          try{  
              URL url = new URL(restUrl);  
              HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
              conn.setRequestMethod("POST");
              
              BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));//转码。  
              String line = null;  
              while ((line = reader.readLine()) != null)  
                  strBuf.append(line + " ");  
                  reader.close();  
          }catch(MalformedURLException e) {  
              e.printStackTrace();   
          }catch(IOException e){  
              e.printStackTrace();   
          }     
          System.out.println(strBuf.toString());
        
          return strBuf.toString();
     }
    public static String  readUnicodeStr(String unicodeStr){  
        StringBuilder buf = new StringBuilder();  
        //因为java转义和正则转义，所以u要这么写  
        String[] cc = unicodeStr.split("\\\\u");  
        for (String c : cc) {  
            if(c.equals(""))  
                continue;  
            int cInt = Integer.parseInt(c, 16);  
            char cChar = (char)cInt;  
            buf.append(cChar);  
        }  
        return buf.toString();  
    }  
      

  
    // tb\u674ea\u661fb  
    public static String readUnicodeStr2(String unicodeStr) {  
        StringBuffer buf = new StringBuffer();  
  
        for (int i = 0; i < unicodeStr.length(); i++) {  
            char char1 = unicodeStr.charAt(i);  
            if (char1 == '\\') {  
                String cStr = unicodeStr.substring(i + 2, i + 6);  
                int cInt = Integer.parseInt(cStr,16);  
                buf.append((char) cInt);  
                // 跨过当前unicode码，因为还有i++，所以这里i加5，而不是6  
                i = i + 5;  
            } else {  
                buf.append(char1);  
            }  
        }  
        return buf.toString();  
    }  
  
    // 判断以index从i开始的串，是不是unicode码  
    private static boolean isUnicode(String unicodeStr, int i) {  
        int len = unicodeStr.length();  
        int remain = len - i;  
        // unicode码，反斜杠后还有5个字符 uxxxx  
        if (remain < 5)  
            return false;  
  
        char flag2 = unicodeStr.charAt(i + 1);  
        if (flag2 != 'u')  
            return false;  
        String nextFour = unicodeStr.substring(i + 2, i + 6);  
        return isHexStr(nextFour);  
    }  
  
    /** hex str 1-9 a-f A-F */  
    private static boolean isHexStr(String str) {  
        for (int i = 0; i < str.length(); i++) {  
            char ch = str.charAt(i);  
            boolean isHex = (ch >= '1' && ch <= '9') || (ch >= 'a' && ch <= 'f')  || (ch >= 'A' && ch <= 'F');  
            if (!isHex)  
                return false;  
        }  
        return true;  
    }  
  
    public static void main(String[] args) {  
      String url= getXmlPath();
      System.out.println(url);
    }  
    
}
