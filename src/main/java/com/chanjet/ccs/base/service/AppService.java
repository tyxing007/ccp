package com.chanjet.ccs.base.service;

import com.chanjet.ccs.base.entity.App;

public interface AppService {
    
    public void save(App app);
    public App findByAppKey(String bssAppKey);
    
}
