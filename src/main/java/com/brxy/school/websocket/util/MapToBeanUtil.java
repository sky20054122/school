/**
 * ChengDu BoRuiXingYun Technology Co., Ltd. CopyRight
 */

package com.brxy.school.websocket.util;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Eric
 * 
 */
public class MapToBeanUtil
{
    /**
     * 将一个 Map转化为一个JavaBean
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> T convertMap(Class<T> type, Map<String,Object> map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException
    {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); 
        T tinst = type.newInstance(); 

        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for(int i = 0; i < propertyDescriptors.length; i++)
        {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            Class propType = descriptor.getPropertyType();
            if(map.containsKey(propertyName))
            {
                Object value = map.get(propertyName);
                
                if(null == value) {
                    continue;
                }
                
                Object[] args = new Object[1];
                
                if(propType.isEnum()) {
                    Enum enumObj = Enum.valueOf(propType, String.valueOf(value));
                    args[0] = enumObj;
                } else {
                    args[0] = value;
                }
                
                descriptor.getWriteMethod().invoke(tinst, args);
            }
        }
        return tinst;
    }

    /**
     * 将一个 JavaBean转化为一个Map
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Map convertBean(Object bean) throws IntrospectionException,
            IllegalAccessException, InvocationTargetException
    {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo
                .getPropertyDescriptors();
        for(int i = 0; i < propertyDescriptors.length; i++)
        {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if(!propertyName.equals("class"))
            {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean, new Object[0]);
                if(result != null)
                {
                    returnMap.put(propertyName, result);
                }
                else
                {
                    returnMap.put(propertyName, "");
                }
            }
        }
        return returnMap;
    }

}
