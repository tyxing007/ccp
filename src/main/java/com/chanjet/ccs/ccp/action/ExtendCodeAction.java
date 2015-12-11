package com.chanjet.ccs.ccp.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.service.CcsService;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;
import com.chanjet.ccs.ccp.base.basic.EnumResult;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;
import com.chanjet.ccs.ccp.base.entity.ExtendCode;
import com.chanjet.ccs.ccp.base.util.CcpUtil;
import com.chanjet.ccs.ccp.service.CcpService;

@Controller
@RequestMapping(value = ConstantsCcp.urlExcode)
public class ExtendCodeAction {
    
    Log logger = LogFactory.getLog(SmsAction.class);
    
    @Resource
    private CcpService ccpService;
    @Resource
    private CcsService ccsService;
    
    // 随机自动分配一个扩展号
    @RequestMapping(value = ConstantsCcp.urlExcodeRandom, method = RequestMethod.POST)
    @ResponseBody
    public Object getRandomExcode(HttpServletRequest request, HttpServletResponse response) {
        
        String business = StringUtils.trimToEmpty(request.getParameter(ConstantsCcp.kBusiness));
//        if (StringUtils.isEmpty(business))
//            return EnumResult.getResult(EnumResult.uniLostParams);
        
        String bssOrgId = (String)request.getAttribute(ConstantsCcs.kBssOrgId);
        String bssUserId = (String)request.getAttribute(ConstantsCcs.kBssUserId);
        
        String bssAppKey = (String)request.getAttribute(ConstantsCcs.kBssAppKey);
        
        // 查询应用验证相关设置
        ResultInner<App> resultApp = ccsService.findByAppKey(bssAppKey);
        if (resultApp.getResultKey().equals(EnumResult.dbNotFindEntity))
            return EnumResult.getResult(EnumResult.appServNotRegistered);
        if (!resultApp.getResult())
            return EnumResult.getResult(resultApp.getResultKey());
        
        App app = resultApp.getData();
        // 应用不可用
        if (app.getStatus() == EnumValues.STATUS_DISABLE)
            return EnumResult.getResult(EnumResult.appServNOtActive);
        
        boolean hasToken = (request.getAttribute(ConstantsCcs.kBssAccessToken) != null);
        
        // 检查是否已经设置扩展号
        ResultInner<ExcodeUserRecord> resultEur = null;
        if (!hasToken)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, "", "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, "", "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, bssOrgId, "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, bssOrgId, bssUserId);
        else 
            return EnumResult.getResult(EnumResult.sysOccurError);
        
        if (resultEur.getResult())
            return EnumResult.getResult(EnumResult.excodeExists);
        
        ResultInner<String> resultCode = null;
        if (!hasToken)
            resultCode = ccpService.randomChoose(app, business, "", "");
        else
            resultCode = ccpService.randomChoose(app, business, bssOrgId, bssUserId);
        if (!resultCode.getResult())
            return EnumResult.getResult(resultCode.getResultKey());
        return EnumResult.modifyInfo(EnumResult.uniSuccess, resultCode.getData());
        
    }
    
    
    // 挑选扩展号
    @RequestMapping(value = ConstantsCcp.urlExcodeChoice, method = RequestMethod.GET)
    @ResponseBody
    public Object getChoice(HttpServletRequest request, HttpServletResponse response) {
        
        String bssAppKey = (String)request.getAttribute(ConstantsCcs.kBssAppKey);
        
        // 查询应用验证相关设置
        ResultInner<App> resultApp = ccsService.findByAppKey(bssAppKey);
        if (resultApp.getResultKey().equals(EnumResult.dbNotFindEntity))
            return EnumResult.getResult(EnumResult.appServNotRegistered);
        if (!resultApp.getResult())
            return EnumResult.getResult(resultApp.getResultKey());
        
        App app = resultApp.getData();
        // 应用不可用
        if (app.getStatus() == EnumValues.STATUS_DISABLE)
            return EnumResult.getResult(EnumResult.appServNOtActive);
        
        ResultInner<List<ExtendCode>> resultExcodes = null;
        if (app.getExcodeType() == EnumValues.EXCODE_STATIC) {
            resultExcodes = ccpService.listStaBySql();
        } else if (app.getExcodeType() == EnumValues.EXCODE_DYNAMIC) {
            resultExcodes = ccpService.listDynBySql();
        }
        
        if(resultExcodes == null){
        	return new ResultInner<String>(EnumResult.sysOccurError);
        }
        if (resultExcodes.getResultKey().equals(EnumResult.dbNotFindEntity))
            return EnumResult.getResult(EnumResult.excodeNotEnough);
        if (!resultExcodes.getResult())
            return EnumResult.getResult(resultExcodes.getResultKey());
                
        String excodesStr = "";
        List<ExtendCode> excodes = resultExcodes.getData();
        if (ConstantsCcp.vExcodeChoiceNum > excodes.size()) {
            for (ExtendCode ec : excodes)
                excodesStr += ec.getCodeName() + Constants.COMMA;
        } else {
            int[] indexes = CcpUtil.getRandomIndex(ConstantsCcp.vExcodeChoiceNum, excodes.size());
            for (int i : indexes)
                excodesStr += excodes.get(i).getCodeName() + Constants.COMMA;
        }
        
        if (excodesStr.length() > 0)
            excodesStr = excodesStr.substring(0, excodesStr.length() - 1);
        
        return EnumResult.modifyInfo(EnumResult.uniSuccess, excodesStr);
    }
    
    
    // 选定扩展号
    @RequestMapping(value = ConstantsCcp.urlExcodeChoose, method = RequestMethod.POST)
    @ResponseBody
    public Object choose(HttpServletRequest request, HttpServletResponse response) {
        
        String codeName = StringUtils.trimToEmpty(request.getParameter(ConstantsCcp.kCodeName));
        String business = StringUtils.trimToEmpty(request.getParameter(ConstantsCcp.kBusiness)); // TODO
        if (StringUtils.isEmpty(codeName))
            return EnumResult.getResult(EnumResult.uniLostParams);
        
        String bssAppKey = (String)request.getAttribute(ConstantsCcs.kBssAppKey);
        String bssOrgId = (String)request.getAttribute(ConstantsCcs.kBssOrgId);
        String bssUserId = (String)request.getAttribute(ConstantsCcs.kBssUserId);
        
        // 查询应用验证相关设置
        ResultInner<App> resultApp = ccsService.findByAppKey(bssAppKey);
        if (resultApp.getResultKey().equals(EnumResult.dbNotFindEntity))
            return EnumResult.getResult(EnumResult.appServNotRegistered);
        if (!resultApp.getResult())
            return EnumResult.getResult(resultApp.getResultKey());
        
        App app = resultApp.getData();
        // 应用不可用
        if (app.getStatus() == EnumValues.STATUS_DISABLE)
            return EnumResult.getResult(EnumResult.appServNOtActive);
        
        boolean hasToken = (request.getAttribute(ConstantsCcs.kBssAccessToken) != null);
        
        // 检查是否已经设置扩展号
        ResultInner<ExcodeUserRecord> resultEur = null;
        if (!hasToken)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, "", "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, "", "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, bssOrgId, "");
        else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER)
            resultEur = ccpService.findEur(EnumValues.EXCODE_STATIC, business, bssAppKey, bssOrgId, bssUserId);
        else 
            return EnumResult.getResult(EnumResult.sysOccurError);
        
        if (resultEur.getResult())
            return EnumResult.getResult(EnumResult.excodeExists);
        
        
        ResultInner<Boolean> resultCode = null;
        if (!hasToken)
            resultCode = ccpService.choose(app, codeName, business, "", "");
        else
            resultCode = ccpService.choose(app, codeName, business, bssOrgId, bssUserId);
        
        if (!resultCode.getResult())
            return EnumResult.getResult(resultCode.getResultKey());
        
        return EnumResult.getResult(EnumResult.uniSuccess);
    }
    
    
}
