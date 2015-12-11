package com.chanjet.ccs.ccp.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.ccp.base.dao.ProxyDao;
import com.chanjet.ccs.ccp.base.entity.Proxy;

@Service
public class ProxyServiceImpl implements ProxyService {
    
    @Resource
    private ProxyDao proxyDao;

    public List<Proxy> list() {
        return proxyDao.listT(QueryNames.proxyList);
    }

}
