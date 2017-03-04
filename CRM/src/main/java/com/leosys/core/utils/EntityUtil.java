/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leosys.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @param <T>
 * @RequestBody 方式
 * @author sam.zheng
 */
public class EntityUtil {
    
    /**
     * 该方法适用于jpa createNativeQuery 查询后，自动转换成list bean 集合
     * @param <T>
     * @param list
     * @param clazz
     * @return
     * @throws Exception 
     */
    public static <T> List<T> castEntity(List<Object[]> list, Class<T> clazz) throws Exception {
        List<T> returnList = new ArrayList<>();
        Object[] co = list.get(0);
        Class[] c2 = new Class[co.length];

        //确定构造方法  
        for (int i = 0; i < co.length; i++) {
            c2[i] = co[i].getClass();
        }

        for (Object[] o : list) {
            Constructor<T> constructor = clazz.getConstructor(c2);
            returnList.add(constructor.newInstance(o));
        }

        return returnList;
    }

    /**
     * 用于将 GET 请求方式传递参数时，自动将参数对于属性字段封装成为实体的方法 POST 表单请求方式不适用，请使用 Spring mvc
     * @param <T>
     * @param request
     * @param clazz
     * @return
     * @throws Exception 
     */
    public static <T> T  convertEntityByHttpRequest(HttpServletRequest request,Class clazz) throws Exception {
        T obj = (T) clazz.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            setObjectValue(request,obj, field);
        }
        return obj;
    }
    
    
    

    /**
     * 动态赋值
     *
     * @param obj
     * @param fieldName
     * @param value
     */
    private static <T>  void setObjectValue(HttpServletRequest request,T obj, Field field) throws Exception {
        String firstLetter = field.getName().substring(0, 1).toUpperCase();
        String setMethodName = "set" + firstLetter + field.getName().substring(1);
        Class fieldTyep = field.getType();
        Method setMethod = obj.getClass().getDeclaredMethod(setMethodName, field.getType());
        Object value = request.getParameter(field.getName());
        if (value != null) {
            if (fieldTyep.equals(String.class)) {
                setMethod.invoke(obj, value);
            } else if (fieldTyep.equals(Integer.class) || fieldTyep.equals(int.class)) {
                setMethod.invoke(obj, Integer.parseInt(value.toString()));
            } else if (fieldTyep.equals(float.class) || fieldTyep.equals(Float.class)) {
                setMethod.invoke(obj, Float.parseFloat(value.toString()));
            } else if (fieldTyep.equals(Date.class)) {
                SimpleDateFormat formatDate = new SimpleDateFormat(DateTimeUtil.DATE);
                Date time = formatDate.parse(value.toString());
                setMethod.invoke(obj, time);
            }
        }
    }
    
    
    
}
