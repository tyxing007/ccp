package com.chanjet.ccs.ccp.base.service;

import com.chanjet.ccs.ccp.base.entity.Sms;

public interface SmsService {

    public void save(Sms sms);
    public Sms find(Integer id);
}
