package com.chanjet.ccs.ccp.base.dao;

import java.util.List;

import com.chanjet.ccs.base.dao.BaseDao;
import com.chanjet.ccs.ccp.base.entity.Deliver;

public interface DeliverDao extends BaseDao<Deliver> {

    public List<Deliver> modifyDeliversBySql(String codeName, boolean forUpdate);
    
}
