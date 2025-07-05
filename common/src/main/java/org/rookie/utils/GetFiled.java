package org.rookie.utils;

import org.rookie.config.BusinessExceptionEnum;

import java.lang.reflect.Field;

public class GetFiled {
    public static Long reflectId(Object object) throws Exception {
        Class<?> clazz = object.getClass();

        while (clazz != null&&!clazz.equals(Object.class)) {
            try{
                Field idField = clazz.getDeclaredField("id");

                idField.setAccessible(true);
                Object idValue = idField.get(object);
                if(idValue==null){
                    throw new RuntimeException("?缓存更新对象的Id怎么是空的?");
                }
                return (Long) idValue;
            }catch (NoSuchFieldException e){
                clazz = clazz.getSuperclass();
            }
        }
        
        throw  BusinessExceptionEnum.GET_FILED_FLIED.exception();
    }
}
