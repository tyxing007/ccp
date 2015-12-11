package com.chanjet.ccs.ccp.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.ccp.base.dao.DeliverDao;
import com.chanjet.ccs.ccp.base.entity.Deliver;

@Service
public class DeliverServiceImpl implements DeliverService {

    @Resource
    private DeliverDao deliverDao;
    
    public List<Deliver> updateDelivers(String codeName) {
        
        List<Deliver> delivers = deliverDao.modifyDeliversBySql(codeName, true);
        return delivers;
        
    }

}
