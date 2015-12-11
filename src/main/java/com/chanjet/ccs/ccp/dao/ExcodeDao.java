package com.chanjet.ccs.ccp.dao;

import java.util.List;

import com.chanjet.ccs.base.dao.BaseDao;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;

public interface ExcodeDao extends BaseDao<ExtendCode>{

    public List<ExtendCode> listBySql(int type, int limit);
    
}
