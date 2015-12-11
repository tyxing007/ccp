package com.chanjet.ccs.base.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.entity.User;
import com.chanjet.ccs.ccp.base.basic.EnumResult;

@Service
public class CcsService {

    private static Log logger = LogFactory.getLog(CcsService.class);
    
    @Resource
    private AppService appService;
    @Resource
    private UserService userService;
    
    
    /** App **/
    public ResultInner<App> save(App app) {
        try {
            appService.save(app);
            return new ResultInner<App>(EnumResult.uniSuccess, app);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<App>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<App> findByAppKey(String bssAppKey) {
        try {
            App app = appService.findByAppKey(bssAppKey);
            if (app == null)
                return new ResultInner<App>(EnumResult.dbNotFindEntity);
            return new ResultInner<App>(EnumResult.uniSuccess, app);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<App>(EnumResult.dbOpeOccurError);
        }
    }
    /** App **/
    
    
    
    /** User **/
    public ResultInner<User> save(User user) {
        try {
            userService.save(user);
            return new ResultInner<User>(EnumResult.uniSuccess, user);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<User>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<User> findUser(String bssAppKey, String bssOrgId, String bssUserId, String service) {
        try {
            User user = userService.find(bssAppKey, bssOrgId, bssUserId, service);
            if (user == null)
                return new ResultInner<User>(EnumResult.dbNotFindEntity);
            return new ResultInner<User>(EnumResult.uniSuccess, user);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<User>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<User> findIsv(String bssAppKey, String bssIsvId, String service) {
        try {
            User user = userService.find(bssAppKey, bssIsvId, service);
            if (user == null)
                return new ResultInner<User>(EnumResult.dbNotFindEntity);
            return new ResultInner<User>(EnumResult.uniSuccess, user);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<User>(EnumResult.dbOpeOccurError);
        }
    }
    
    /** User **/
    
    
}
