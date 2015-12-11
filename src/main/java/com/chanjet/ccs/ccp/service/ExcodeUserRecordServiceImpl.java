package com.chanjet.ccs.ccp.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.basic.QueryNames;
import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.util.DateUtil;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;
import com.chanjet.ccs.ccp.dao.ExcodeDao;
import com.chanjet.ccs.ccp.dao.ExcodeUserRecordDao;


// 要有事务支持，出现异常时回滚
@Service
public class ExcodeUserRecordServiceImpl implements ExcodeUserRecordService {
    
    private static Log logger = LogFactory.getLog(ExcodeUserRecordServiceImpl.class);

    @Resource
    private ExcodeUserRecordDao excodeUserRecordDao;
    @Resource
    private ExcodeDao excodeDao;
    
    public void saveT(ExcodeUserRecord eur) {
        if (eur.getId() > 0)
            excodeUserRecordDao.updateT(eur);
        else 
            excodeUserRecordDao.saveT(eur);
    }

    public ExcodeUserRecord find(int type, String business, String bssAppKey, String bssOrgId, String bssUserId) {
        
        business = StringUtils.trimToEmpty(business);
        
        List<ExcodeUserRecord> eurs = excodeUserRecordDao.listT(QueryNames.excodeUserListByAppOrgUser, type, business, bssAppKey, bssOrgId, bssUserId);
        Date now = new Date();
        for (ExcodeUserRecord eur : eurs) {
            if (eur.getStartDateTime().before(now) && eur.getEndDateTime().after(now) )
                return eur;
        }
        return null;
    }
    
    // 随机选取扩展号
    public String randomChoose(App app, String business, String bssOrgId, String bssUserId) {
        
        ExcodeUserRecord record = new ExcodeUserRecord();
        
        List<ExtendCode> codes = null;
        if (app.getExcodeType() == EnumValues.EXCODE_DYNAMIC) {
            codes = excodeDao.listT(QueryNames.excodeListDyn);
            record.setType(EnumValues.EXCODE_DYNAMIC);
        } else if (app.getExcodeType() == EnumValues.EXCODE_STATIC) {
            codes = excodeDao.listT(QueryNames.excodeListSta);
            record.setType(EnumValues.EXCODE_STATIC);
        }
        
        if (codes == null || codes.size() == 0)
            return null;
        
        int index = (int)(Math.random() * codes.size());
        ExtendCode excode = codes.get(index);
        
        excode.setState(EnumValues.EXCODE_ACTIVE);
        
        record.setCodeName(excode.getCodeName());
        record.setWhose(app.getWhoseExcode());
        record.setBusiness(business);
        record.setBssAppId(app.getBssAppId());
        record.setBssAppKey(app.getBssAppKey());
        record.setStartDateTime(new Date()); // TODO 需要配置
        record.setEndDateTime(DateUtil.addYears(new Date(), 1)); // TODO 需要配置
        record.setStatus(EnumValues.EXCODE_ACTIVE);

        if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG) {
            record.setBssOrgId(bssOrgId);
        } else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER) {
            record.setBssOrgId(bssOrgId);
            record.setBssUserId(bssUserId);
        }
        
        excodeDao.updateT(excode);
        excodeUserRecordDao.saveT(record);
        return excode.getCodeName();
    }

    public boolean choose(App app, String codeName, String business, String bssOrgId, String bssUserId){
        
        ExcodeUserRecord record = new ExcodeUserRecord();
        record.setCodeName(codeName);
        record.setWhose(app.getWhoseExcode());
        record.setBusiness(business);
        record.setBssAppId(app.getBssAppId());
        record.setBssAppKey(app.getBssAppKey());
        record.setStartDateTime(new Date()); // TODO 需要配置
        record.setEndDateTime(DateUtil.addYears(new Date(), 1)); // TODO 需要配置
        record.setStatus(EnumValues.EXCODE_ACTIVE);
        
        if (app.getExcodeType() == EnumValues.EXCODE_STATIC)
            record.setType(EnumValues.EXCODE_STATIC);
        else if (app.getExcodeType() == EnumValues.EXCODE_DYNAMIC)
            record.setType(EnumValues.EXCODE_DYNAMIC);

        if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG) {
            record.setBssOrgId(bssOrgId);
        } else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER) {
            record.setBssOrgId(bssOrgId);
            record.setBssUserId(bssUserId);
        }
        
        int result = excodeDao.updateT(QueryNames.excodeUpdate, codeName, record.getType());
        if (result > 0) {
            excodeUserRecordDao.saveT(record);  // TODO 需要测试保存失败时，扩展码表是否会回滚
            return true;
        }
        return false;
    }
}
