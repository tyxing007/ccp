package com.chanjet.ccs.base.service;

import com.chanjet.ccs.base.entity.User;

public interface UserService {

    public void save(User user);
    public User find(String bssAppKey, String bssOrgId, String bssUserId, String service);
    public User find(String bssAppKey, String bssIsvId, String service);
}
