package com.qtdbp.security.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 实体工具类
 *
 * @author: caidchen
 * @create: 2017-05-11 15:57
 * To change this template use File | Settings | File Templates.
 */
public class EntityUtil {

    /**
     * 获得实体属性
     * @param clazz
     * @return
     */
    public static PropertyDescriptor getPrimaryKey(Class<?> clazz) {

        StringBuffer sb = new StringBuffer();//构建一个可变字符串用来构建方法名称
        Method setMethod = null;
        Method getMethod = null;
        PropertyDescriptor pd = null;
        try {
            Field[] fs = clazz.getDeclaredFields() ; // 获取所有字段
            if (fs != null && fs.length > 0) {

                for (Field f : fs) {

                }

                //构建方法的后缀
                String methodEnd = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
                sb.append("set" + methodEnd);//构建set方法
                setMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ f.getType() });
                sb.delete(0, sb.length());//清空整个可变字符串
                sb.append("get" + methodEnd);//构建get方法
                //构建get 方法
                getMethod = clazz.getDeclaredMethod(sb.toString(), new Class[]{ });
                //构建一个属性描述器 把对应属性 propertyName 的 get 和 set 方法保存到属性描述器中
                pd = new PropertyDescriptor(propertyName, getMethod, setMethod);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pd;
    }
}
