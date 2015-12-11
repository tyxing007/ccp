package com.chanjet.ccs.ccp.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.ccp.base.dao.SmsDao;
import com.chanjet.ccs.ccp.base.entity.Sms;

@Service
public class SmsServiceImpl implements SmsService {
    
    @Resource
    private SmsDao smsDao;
    
    public void save(Sms sms) {
        if (sms.getId() > 0)
            smsDao.updateT(sms);
        else
            smsDao.saveT(sms);
    }
    
    public Sms find(Integer id) {
        return smsDao.getT(Sms.class, id);
    }
}
