package com.chanjet.ccs.ccp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;
import com.chanjet.ccs.ccp.dao.ExcodeDao;

@Service
public class ExcodeServiceImpl implements ExcodeService{

    
    private static Log logger = LogFactory.getLog(ExcodeServiceImpl.class);
    private static String info = "操作数据库时发生错误  => ";
    
    @Resource
    private ExcodeDao excodeDao;
    
    public void save(ExtendCode excode) {
        if (excode.getId() > 0)
            excodeDao.updateT(excode);
        else
            excodeDao.saveT(excode);
        
    }
    
    public List<ExtendCode> listSta() {
        return excodeDao.listT(QueryNames.excodeListSta);
    }

    public List<ExtendCode> listDyn() {
        return excodeDao.listT(QueryNames.excodeListDyn);
    }
    
    public List<ExtendCode> listStaBySql() {
        return excodeDao.listBySql(EnumValues.EXCODE_STATIC, ConstantsCcp.vExcodeLimit);
    }
    
    public List<ExtendCode> listDynBySql() {
        return excodeDao.listBySql(EnumValues.EXCODE_DYNAMIC, ConstantsCcp.vExcodeLimit);
    }
    
    public boolean update(String codeName, int excodeType) {
        int result = excodeDao.updateT(QueryNames.excodeUpdate, codeName, excodeType);
        if (result > 0)
            return true;
        return false;
        
    }
    
    
}
