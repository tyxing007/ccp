package com.chanjet.ccs.base.service;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.util.DataWraper;

@Service(value = "redisUtil")
public class RedisUtil {
    
    private static Log log = LogFactory.getLog(RedisUtil.class);
    
    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    public StringRedisTemplate srt;
    
    @Autowired
    @Qualifier(value = "redisTemplate")
    public RedisTemplate rt;
    
    
    public String getString(String key) {
        try {
            return srt.opsForValue().get(key);
        } catch (Exception ex) {
            log.error("redis error", ex);
            return null;
        }
    }
    
    public void setString(String key, String value) {
        try {
            srt.opsForValue().set(key, value);
        } catch (Exception ex) {
            log.error("redis error", ex);
        }
    }
    
    public boolean setString(String key, String value, int timeout) {
        try {
            srt.opsForValue().set(key, value);
            boolean result = srt.expire(key, timeout, TimeUnit.MILLISECONDS);
            return result;
        } catch (Exception ex) {
            log.error("redis error", ex);
            return false;
        }
    }
    
    public <T> T getObject(String key, Class clazz) {
        try {
            String json = srt.opsForValue().get(key);
            if (json == null)
                return null;
            return (T)DataWraper.wrapJson2Object(json, clazz);
        } catch (Exception ex) {
            log.error("redis error", ex);
            return null;
        }
    }
    
    public <T> void setObject(String key, T obj) {
        try {
            String value = DataWraper.wrap2Json(obj);
            srt.opsForValue().set(key, value);
        } catch (Exception ex) {
            log.error("redis error", ex);
        }
    }
    
    public <T> boolean setObject(String key, T obj, int timeout) {
        try {
            String value = DataWraper.wrap2Json(obj);
            srt.opsForValue().set(key, value);
            boolean result = srt.expire(key, timeout, TimeUnit.MILLISECONDS);
            return result;
        } catch (Exception ex) {
            log.error("redis error", ex);
            return false;
        }
    }
    
    public void delete(String key) {
        try {
            srt.delete(key);
        } catch (Exception ex) {
            log.error("redis error", ex);
        }
    }
    
    public void setObjSerial(final String key, final Serializable obj) {
        try {
            rt.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    conn.set(rt.getStringSerializer().serialize(key), rt.getHashValueSerializer().serialize(obj));
                    return null;
                };
            });
        } catch (Exception ex) {
            log.error("redis error", ex);
        }
    }
    
    public void setObjSerial(final String key, final Serializable obj, int timeout) {
        try {
            rt.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    conn.set(rt.getStringSerializer().serialize(key), rt.getHashValueSerializer().serialize(obj));
                    return null;
                };
            });
            rt.expire(rt.getStringSerializer().serialize(key), timeout, TimeUnit.SECONDS);
        } catch (Exception ex) {
            log.error("redis error", ex);
        }
        
    }
    
    public Object getObjSerial(final String key) {
        try {
            return rt.execute(new RedisCallback<Object>() {
                public Object doInRedis(RedisConnection conn) throws DataAccessException {
                    byte[] keyByte = rt.getStringSerializer().serialize(key);
                    if (conn.exists(keyByte)) {
                        byte[] valueByte = conn.get(keyByte);
                        Object value = rt.getHashValueSerializer().deserialize(valueByte);
                        return value;
                    }
                    return null;
                }
            });
        } catch (Exception ex) {
            log.error("redis error", ex);
            return null;
        }
    }
    
    
}
