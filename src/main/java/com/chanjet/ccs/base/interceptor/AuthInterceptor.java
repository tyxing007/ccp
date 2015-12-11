package com.chanjet.ccs.base.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chanjet.ccs.base.basic.Constants;
import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.basic.ConstantsHttp;
import com.chanjet.ccs.base.entity.Result;
import com.chanjet.ccs.base.service.RedisUtil;
import com.chanjet.ccs.base.util.AuthHelper;
import com.chanjet.ccs.base.util.ReqRespHelper;

public class AuthInterceptor extends HandlerInterceptorAdapter {
    
    private static Log logger = LogFactory.getLog(AuthInterceptor.class);
    
    @Resource
    private RedisUtil redisUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        
        String authorization = StringUtils.trimToEmpty(request.getHeader(ConstantsHttp.H_AUTH));
        Result result = AuthHelper.checkAuth(authorization, request, redisUtil);
        if (result != null) {
            logger.info(request.getAttribute(ConstantsCcs.kReqIpAndTime) + Constants.DOUB_COLON + result);
            return ReqRespHelper.setAndWriteResult(response, result);
        }

        return true;
    }
    
}
