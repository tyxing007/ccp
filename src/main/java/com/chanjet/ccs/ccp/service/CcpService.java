package com.chanjet.ccs.ccp.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.ccp.base.basic.EnumResult;
import com.chanjet.ccs.ccp.base.entity.Deliver;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;
import com.chanjet.ccs.ccp.base.entity.Sms;
import com.chanjet.ccs.ccp.base.service.DeliverService;
import com.chanjet.ccs.ccp.base.service.SmsService;

@Service
public class CcpService {
    
    private static Log logger = LogFactory.getLog(CcpService.class);
    
    @Resource
    private SmsService smsService;
    @Resource
    private ExcodeService excodeService;
    @Resource
    private ExcodeUserRecordService eurService;
    @Resource
    private DeliverService deliverService;
    
    
    /** Sms **/
    public ResultInner<Sms> save(Sms sms) {
        try {
            smsService.save(sms);
            return new ResultInner<Sms>(EnumResult.uniSuccess, sms);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<Sms>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<Sms> findSms(Integer id) {
        try {
            Sms sms = smsService.find(id);
            if (sms == null)
                return new ResultInner<Sms>(EnumResult.dbNotFindEntity);
            return new ResultInner<Sms>(EnumResult.uniSuccess, sms);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<Sms>(EnumResult.dbOpeOccurError);
        }
    }
    /** Sms **/
    
    
    
    /** ExtendCode **/
    public ResultInner<ExtendCode> save(ExtendCode excode) {
        try {
            excodeService.save(excode);
            return new ResultInner<ExtendCode>(EnumResult.uniSuccess, excode);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<ExtendCode>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<List<ExtendCode>> listSta() {
        try {
            List<ExtendCode> excodes = excodeService.listSta(); // TODO 验证是空list还是null
            if (excodes.size() == 0)
                return new ResultInner<List<ExtendCode>>(EnumResult.dbNotFindEntity);
            return new ResultInner<List<ExtendCode>>(EnumResult.uniSuccess, excodes);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<List<ExtendCode>>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<List<ExtendCode>> listDyn() {
        try {
            List<ExtendCode> excodes = excodeService.listDyn(); // TODO 验证是空list还是null
            if (excodes.size() == 0)
                return new ResultInner<List<ExtendCode>>(EnumResult.dbNotFindEntity);
            return new ResultInner<List<ExtendCode>>(EnumResult.uniSuccess, excodes);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<List<ExtendCode>>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<List<ExtendCode>> listStaBySql() {
        try {
            List<ExtendCode> excodes = excodeService.listStaBySql(); // TODO 验证是空list还是null
            if (excodes.size() == 0)
                return new ResultInner<List<ExtendCode>>(EnumResult.dbNotFindEntity);
            return new ResultInner<List<ExtendCode>>(EnumResult.uniSuccess, excodes);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<List<ExtendCode>>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<List<ExtendCode>> listDynBySql() {
        try {
            List<ExtendCode> excodes = excodeService.listDynBySql(); // TODO 验证是空list还是null
            if (excodes.size() == 0)
                return new ResultInner<List<ExtendCode>>(EnumResult.dbNotFindEntity);
            return new ResultInner<List<ExtendCode>>(EnumResult.uniSuccess, excodes);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<List<ExtendCode>>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<Boolean> updateExcode(String codeName, int excodeType) {
        try {
            boolean result = excodeService.update(codeName, excodeType);
            if (result)
                return new ResultInner<Boolean>(EnumResult.uniSuccess, result);
            return new ResultInner<Boolean>(EnumResult.excodeChooseError);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<Boolean>(EnumResult.dbOpeOccurError);
        }
    }
    /** ExtendCode **/
    
    
    /** ExcodeUserRecord **/
    public ResultInner<ExcodeUserRecord> saveT(ExcodeUserRecord eur) {
        try {
            eurService.saveT(eur);
            return new ResultInner<ExcodeUserRecord>(EnumResult.uniSuccess, eur);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<ExcodeUserRecord>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<ExcodeUserRecord> findEur(int type, String business, String bssAppKey, String bssOrgId, String bssUserId) {
        try {
            ExcodeUserRecord eur = eurService.find(type, business, bssAppKey, bssOrgId, bssUserId);
            if (eur == null)
                return new ResultInner<ExcodeUserRecord>(EnumResult.dbNotFindEntity);
            return new ResultInner<ExcodeUserRecord>(EnumResult.uniSuccess, eur);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<ExcodeUserRecord>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<String> randomChoose(App app, String business, String bssOrgId, String bssUserId) {
        try {
            String codeName = eurService.randomChoose(app, business, bssOrgId, bssUserId);
            if (codeName == null)
                return new ResultInner<String>(EnumResult.excodeNotEnough);
            return new ResultInner<String>(EnumResult.uniSuccess, codeName);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<String>(EnumResult.dbOpeOccurError);
        }
    }
    
    public ResultInner<Boolean> choose(App app, String codeName, String business, String bssOrgId, String bssUserId) {
        try {
            boolean result = eurService.choose(app, codeName, business, bssOrgId, bssUserId);
            if (result)
                return new ResultInner<Boolean>(EnumResult.uniSuccess, result);
            return new ResultInner<Boolean>(EnumResult.excodeChooseError);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<Boolean>(EnumResult.dbOpeOccurError);
        }
    }
    /** ExcodeUserRecord **/
    
    
    
    /** Deliver **/
    public ResultInner<List<Deliver>> updateDelivers(String codeName) {
        try {
            List<Deliver> delivers = deliverService.updateDelivers(codeName);
            return new ResultInner<List<Deliver>>(EnumResult.uniSuccess, delivers);
        } catch (Exception ex) {
            logger.error("db error", ex);
            return new ResultInner<List<Deliver>>(EnumResult.dbOpeOccurError);
        }
        
    }
    /** Deliver **/
}
