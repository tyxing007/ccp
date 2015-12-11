package com.chanjet.ccs.common.sdk.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class DataWraper {

    
    private static final String clazz = "class";
    private static final ObjectMapper mapper = new ObjectMapper(); 
    
    private static final String info = "不能解析JSON串：";

    
    // 将JSON串转换成Map形式的数据（均为String类型）
    public static Map<String, String> wrapJson2MapStr(String json) {
        Map<String, String> result = new HashMap<String, String>();
        try {
            JsonNode node = mapper.readTree(json);
            Iterator<String> fields = node.getFieldNames();
            while (fields.hasNext()) {
                String f = fields.next();
                result.put(f, node.findValue(f).getTextValue().trim());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    // 将JSON串转换成Map形式的数据
    public static Map<String, Object> wrapJson2MapObj(String json) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            JsonNode node = mapper.readTree(json);
            Iterator<String> fields = node.getFieldNames();
            
            while (fields.hasNext()) {
                String f = fields.next();
                JsonNode value = node.findValue(f);
                if (value.isBoolean()) {
                    result.put(f, value.getBooleanValue());
                } else if (value.isInt()) {
                    result.put(f,  value.getIntValue());
                } else if (value.isDouble()) {
                    result.put(f, value.getDoubleValue());
                } else {
                    result.put(f, node.findValue(f).getTextValue().trim());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    // 将JSON直接封装为对象
    public static Object wrapJson2Object(String json, Class clazz) {
        Object o = null;
        try {
            o = mapper.readValue(json, clazz);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }
    
    
    // TODO 未过滤
    public static String wrap2Json(Object obj, List<String> config) {
        ObjectMapper mapper = new ObjectMapper(); 
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    
    public static Map<String, String> wrap2Map(Object o) {
        return wrap2Map(o, null);
    }

    // 不支持复杂的对象类型
    public static Map<String, String> wrap2Map(Object obj, List<String> config) {
        
        try {
            Map<String, String> result = new HashMap<String, String>();
            Map<String, String> source = BeanUtils.describe(obj);
            if (obj instanceof Map)
                source = (Map)obj;
            
            source.remove(clazz);
            if (config == null) {
                result = source;
            } else {
                for (String c : config)
                    result.put(c, source.get(c));
            }
            return result;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String wrap2Json(Object obj) {
        return wrap2Json(obj, null);
    }
    
    
    public static void main(String[] args) {
        String s = "";
        System.out.println(wrapJson2MapStr(null));
        
    }
}
