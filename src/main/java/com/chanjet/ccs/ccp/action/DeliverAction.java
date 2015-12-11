package com.chanjet.ccs.ccp.action;

import java.util.ArrayList;
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

import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.entity.App;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.service.CcsService;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;
import com.chanjet.ccs.ccp.base.basic.EnumResult;
import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.entity.Deliver;
import com.chanjet.ccs.ccp.base.entity.ExcodeUserRecord;
import com.chanjet.ccs.ccp.base.entity.ResultDataDeliver;
import com.chanjet.ccs.ccp.service.CcpService;


@Controller
@RequestMapping(value = ConstantsCcp.urlDeliver)
public class DeliverAction {
    
    private static Log logger = LogFactory.getLog(DeliverAction.class);
    
    @Resource
    private CcpService ccpService;
    @Resource
    private CcsService ccsService;
    
    
    // 获取回执
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Object getDelivers(HttpServletRequest request, HttpServletResponse response) {
        
        String business = StringUtils.trimToEmpty(request.getParameter(ConstantsCcp.kBusiness));
//        if (StringUtils.isEmpty(business))
//            return EnumResult.getResult(EnumResult.uniLostParams);
        
        //String bssAppId = (String)request.getAttribute(ConstantsCcs.kBssAppId);
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
        
        int excodeType = app.getExcodeType();
        ResultInner<ExcodeUserRecord> resultEur = null;
        if (excodeType  == EnumValues.EXCODE_STATIC) {
            if (!hasToken)
                resultEur = ccpService.findEur(excodeType, business, bssAppKey, "", "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP)
                resultEur = ccpService.findEur(excodeType, business, bssAppKey, "", "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_ORG)
                resultEur = ccpService.findEur(excodeType, business, bssAppKey, bssOrgId, "");
            else if (app.getWhoseExcode() == EnumValues.EXCODE_APP_USER)
                resultEur = ccpService.findEur(excodeType, business, bssAppKey, bssOrgId, bssUserId);
            
        }
//        else if (excodeType == EnumValues.EXCODE_DYNAMIC) {
//            // TODO 尚未支持动态
//        }
        if(resultEur == null){
        	return EnumResult.sysOccurError;
        }
        if (!resultEur.getResult())
        	return EnumResult.getResult(resultEur.getResultKey());
        
        ExcodeUserRecord eur = resultEur.getData();
        
        ResultInner<List<Deliver>> resultDelivers = ccpService.updateDelivers(eur.getCodeName());
        if (!resultDelivers.getResult())
            return EnumResult.getResult(resultDelivers.getResultKey());
        
        List<Deliver> delivers = resultDelivers.getData();
        List<ResultDataDeliver> returnData = new ArrayList<ResultDataDeliver>();
        for(Deliver d : delivers)
            returnData.add(new ResultDataDeliver(d.getMobile(), d.getContent(), d.getCreateTime()));
        
        return EnumResult.modifyInfo(EnumResult.uniSuccess, returnData.toString());
    }
    
}
