package com.chanjet.ccs.ccp.service;

import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;

public interface ExcodeUserRecordService {
    
    public void saveT(ExcodeUserRecord eur);
    public ExcodeUserRecord find(int type, String business, String bssAppKey, String bssOrgId, String bssUserId);
    public String randomChoose(App app, String business, String bssOrgId, String bssUserId);
    public boolean choose(App app, String codeName, String business, String bssOrgId, String bssUserId);
    
}
