/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author fanyy
 */
public class MapUtils {
        public static void setValue(Map map,Object thisObj)  
    {  
      Set set = map.keySet();  
      Iterator iterator = set.iterator();  
      while (iterator.hasNext())  
      {  
        Object obj = iterator.next();  
        Object val = map.get(obj);  
        setMethod(obj, val, thisObj);  
      }  
    }  
        public static void setMethod(Object method, Object value ,Object thisObj)  
      {  
        Class c;  
        try  
        {  
          c = Class.forName(thisObj.getClass().getName());  
          String met = (String) method;  
          met = met.trim();  
          if (!met.substring(0, 1).equals(met.substring(0, 1).toUpperCase()))  
          {  
            met = met.substring(0, 1).toUpperCase() + met.substring(1);  
          }  
          if (!String.valueOf(method).startsWith("set"))  
          {  
            met = "set" + met;  
          }  
          Class types[] = new Class[1];  
          types[0] = Class.forName("java.lang.String");  
          Method m = c.getMethod(met, types);  
          m.invoke(thisObj, value);  
        }  
        catch (Exception e)  
        {  
          // TODO: handle exception  
          e.printStackTrace();  
        }  
      }  
            public static Map getValue(Object thisObj)  
      {  
        Map map = new HashMap();  
        Class c;  
        try  
        {  
          c = Class.forName(thisObj.getClass().getName());  
          Method[] m = c.getMethods();  
          for (int i = 0; i < m.length; i++)  
          {  
            String method = m[i].getName();  
            if (method.startsWith("get"))  
            {  
              try{  
              Object value = m[i].invoke(thisObj);  
              if (value != null)  
              {  
                String key=method.substring(3);  
                key=key.substring(0,1).toUpperCase()+key.substring(1);  
                map.put(method, value);  
              }  
              }catch (Exception e) {  
                // TODO: handle exception  
                System.out.println("error:"+method);  
              }  
            }  
          }  
        }  
        catch (Exception e)  
        {  
          // TODO: handle exception  
          e.printStackTrace();  
        }  
        return map;  
      }
             public Map<String, Integer> dateUtil(String bTime, String eTime, Integer dateType) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if (dateType == 1) {
            bTime = bTime + "-01";
            eTime = eTime + "-12";
        }
        try {
            Date bDate = sdf.parse(bTime);
            Date eDate = sdf.parse(eTime);
             Calendar b = Calendar.getInstance();
             b.setTime(bDate);
             Calendar e = Calendar.getInstance();
             e.setTime(eDate);
            int byr = b.get(Calendar.YEAR);
            int bmouth = b.get(Calendar.MONTH);
            int eyr = e.get(Calendar.YEAR);
            int emouth = e.get(Calendar.MONTH);
            map.put("byr", byr);
            map.put("bmouth", bmouth+1);
            map.put("eyr", eyr);
            map.put("emouth", emouth+1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }
}
