package com.chanjet.ccs.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.dao.UserDao;
import com.chanjet.ccs.base.entity.User;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    
    public void save(User user) {
        if (user.getId() > 0)
            userDao.updateT(user);
        else
            userDao.saveT(user);
    }
    
    public User find(String bssAppKey, String bssOrgId, String bssUserId, String service) {
        List<User> users = userDao.listT(QueryNames.userListByIdsAndServ, bssAppKey, bssOrgId, bssUserId, service);
        return users.size() > 0 ? users.get(0) : null;
    }
    
    
    public User find(String bssAppKey, String bssIsvId, String service) {
        List<User> users = userDao.listT(QueryNames.isvListByIdsAndServ, bssAppKey, bssIsvId, service);
        return users.size() > 0 ? users.get(0) : null;
    }
}
