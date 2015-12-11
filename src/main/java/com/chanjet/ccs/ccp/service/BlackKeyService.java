package com.chanjet.ccs.ccp.service;

import java.util.List;

import com.chanjet.ccs.ccp.base.entity.BlackKey;


public interface BlackKeyService {
    
    public void saveT(BlackKey blackKey);
    public List<BlackKey> getBlackKeys();
    
}
