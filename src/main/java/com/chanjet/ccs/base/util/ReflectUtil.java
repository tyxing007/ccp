package com.chanjet.ccs.base.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class ReflectUtil {

    
    // 获取实体类的属性名称
    public static List<String> getAttrNames(Class clazz){
        
        List<String> names = new ArrayList<String>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields)
            names.add(f.getName());
        
        Class parentClazz = clazz.getSuperclass();
        if (!parentClazz.getSimpleName().equals("Object")) {
            List<String> parentAttrNames = getAttrNames(parentClazz);
            names.addAll(parentAttrNames);
        }
        
        return names;
    }
    
}
