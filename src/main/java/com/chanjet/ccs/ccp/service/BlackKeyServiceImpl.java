package com.chanjet.ccs.ccp.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.ccp.base.entity.BlackKey;
import com.chanjet.ccs.ccp.dao.BlackKeyDao;

@Service
public class BlackKeyServiceImpl implements BlackKeyService {

    @Resource
    BlackKeyDao blackKeyDao;
    
    public void saveT(BlackKey blackKey) {
        blackKeyDao.saveT(blackKey);
    }

    public List<BlackKey> getBlackKeys() {
        return blackKeyDao.listT(QueryNames.bkList);
    }
}
