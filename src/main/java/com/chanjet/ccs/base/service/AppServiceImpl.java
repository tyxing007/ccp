package com.chanjet.ccs.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.dao.AppDao;
import com.chanjet.ccs.base.entity.App;


@Service
public class AppServiceImpl implements AppService {
    
//    private static Log logger = LogFactory.getLog(AppServiceImpl.class);
    
//    private static String info = "操作数据库时发生错误  => ";

    @Resource
    private AppDao appDao;
    
    public void save(App app) {
        if (app.getId() > 0)
            appDao.updateT(app);
        else
            appDao.saveT(app);
    }
    
    public App findByAppKey(String bssAppKey) {
        List<App> apps = appDao.listT(QueryNames.appListByAppKey, bssAppKey);
        return apps.size() > 0 ? apps.get(0) : null;
        
    }

}
