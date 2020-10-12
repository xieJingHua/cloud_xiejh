package com.xiejh.hystrix_service.utils;

import org.apache.commons.lang.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Tzx
 */
public class MyBeanUtils {
    private static Logger log = LoggerFactory.getLogger(MyBeanUtils.class);

    /**
     * bean转化为map
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return null;
        } else {
            HashMap<String, Object> hashMap = new HashMap<>();

            try {
                Class<?> c = bean.getClass();
                Method[] methods = c.getMethods();

                for (Method method : methods) {
                    String name = method.getName();
                    String key = "";
                    if (name.startsWith("get")) {
                        key = name.substring(3);
                    }

                    if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0) {
                        if (key.length() == 1) {
                            key = key.toLowerCase();
                        } else if (!Character.isUpperCase(key.charAt(1))) {
                            key = key.substring(0, 1).toLowerCase() + key.substring(1);
                        }

                        if (!"class".equals(key.toLowerCase())) {
                            Object value = method.invoke(bean);
                            if (value != null) {
                                hashMap.put(key, value);
                            }
                        }
                    }
                }
            } catch (Throwable var9) {
                var9.printStackTrace();
            }

            return hashMap;
        }
    }

    /**
     * 将map转换为bean
     *
     * @param clazz
     * @param map
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Class<T> clazz, Map<String, Object> map) {
        T bean = null;
        try {
            bean = clazz.newInstance();
            for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
                String key = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                // 去这个类及其父类递归找到这个属性
                Field field = getClassField(clazz, key);
                if (field != null) {
                    // 获取属性的set方法
                    String methodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                    Method method = clazz.getMethod(methodName, field.getType());
                    method.invoke(bean, value);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            log.error("columnUpperCaseToBean : ======================Map转化Bean方法异常===================");
            e.printStackTrace();
        }
        return bean;
    }

    private static Field getClassField(Class<?> clazz, String fieldName) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals(fieldName)) {
                return declaredField;
            }
        }
        // 如果找不到对应属性。递归去父类找
        Class<?> superclass = clazz.getSuperclass();
        if (null != superclass) {
            return getClassField(superclass, fieldName);
        }
        return null;
    }

    /**
     * 大写带下划线的属性列,转化为驼峰命名风格的 MAP
     *
     * @param stringObjectMap
     * @return
     */
    public Map<String, Object> columnUpperCaseToMap(Map<String, Object> stringObjectMap) {
        Set<Map.Entry<String, Object>> entrySet = stringObjectMap.entrySet();
        Map<String, Object> map = new HashMap<>(entrySet.size());
        for (Map.Entry<String, Object> stringObjectEntry : entrySet) {
            String key = stringObjectEntry.getKey();
            Object value = stringObjectEntry.getValue();
            String newKey = WordUtils.capitalizeFully(key, new char[]{'_'}).replace("_", "");
            String s = newKey.substring(0, 1).toLowerCase() + newKey.substring(1);
            map.put(s, value);
        }
        return map;
    }
}