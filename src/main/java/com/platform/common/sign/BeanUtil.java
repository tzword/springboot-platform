package com.platform.common.sign;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang3.StringUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;

/**
 * @author jianghy
 * @Description: BeanUtil工具类
 * @date 2020/6/28 11:05
 */
public class BeanUtil {

    // Map --> Bean 2: 利用org.apache.commons.beanutils 工具类实现 Map --> Bean
    public static void transMap2Bean2(Map<String, Object> map, Object obj) {
        if (map == null || obj == null) {
            return;
        }
        try {
            BeanUtils.populate(obj, map);
        } catch (Exception e) {
            System.out.println("transMap2Bean2 Error " + e);
        }
    }

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (null != value && !"".equals(value))
                        map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;

    }

    public static String mapOrderStr(Map<String, Object> map) {
        List<Entry<String, Object>> list = new ArrayList<Entry<String, Object>>(map.entrySet());
        Collections.sort(list, new Comparator<Entry<String, Object>>() {
            public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        StringBuilder sb = new StringBuilder();
        for (Entry<String, Object> mapping : list) {
            sb.append(mapping.getKey() + "=" + mapping.getValue() + "&");
        }
        return sb.substring(0, sb.length() - 1);
    }


    /**
     * @param dest 1
     * @param src  2
     * @return void
     * @throws
     * @Description: 将源的属性复制到目标属性上去
     * @author jianghy
     * @date 2020/6/28 13:14
     */
    public static void copyProperties(Object dest, Object src) {
        if (src == null || dest == null) {
            return;
        }
        // 获取所有的get/set 方法对应的属性
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(src);

        for (int i = 0; i < descriptors.length; i++) {
            PropertyDescriptor propItem = descriptors[i];
            // 过滤setclass/getclass属性
            if ("class".equals(propItem.getName())) {
                continue;
            }

            try {
                Method method = propItem.getReadMethod();
                // 通过get方法获取对应的值
                Object val = method.invoke(src);
                // 如果是空，不做处理
                if (null == val) {
                    continue;
                }
                if (val instanceof String) {
                    if (StringUtils.isBlank(val.toString())) {
                        continue;
                    }
                }
                // 值复制
                PropertyDescriptor prop = propertyUtilsBean.getPropertyDescriptor(dest, propItem.getName());
                // 调用写方法，设置值
                if (null != prop && prop.getWriteMethod() != null) {
                    prop.getWriteMethod().invoke(dest, val);
                }
            } catch (Exception e) {
            }

        }

    }

    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for (Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object != null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;

    }

}

