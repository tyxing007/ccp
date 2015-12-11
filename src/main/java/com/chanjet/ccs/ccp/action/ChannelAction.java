package com.chanjet.ccs.ccp.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chanjet.ccs.base.service.RedisUtil;
import com.chanjet.ccs.ccp.base.basic.ConstantsCcp;


@Controller
@RequestMapping(value = ConstantsCcp.urlChannel)
public class ChannelAction {
    
    private static String poolPre = "channelPool";
    @Resource
    private RedisUtil redisUtil;
    
    
    // 清除通道缓存
    @RequestMapping(value = "/initChannel", method = RequestMethod.GET)
    @ResponseBody
    public Object initChannel(HttpServletRequest request, HttpServletResponse response) {
        redisUtil.delete(ConstantsCcp.vCacheEnginePre + poolPre);
        return Boolean.TRUE;
    }
    
    // 清除通道缓存
    @RequestMapping(value = "/markChannelAvailable", method = RequestMethod.GET)
    @ResponseBody
    public Object markChannelAvailable(HttpServletRequest request, HttpServletResponse response,String channelId) {
      
      //预留
        
        return "TODO";
    }
    
    // 清除通道缓存
    @RequestMapping(value = "/MarkChannelUnavailable", method = RequestMethod.GET)
    @ResponseBody
    public Object MarkChannelUnavailable(HttpServletRequest request, HttpServletResponse response,String channelId) {
      
       //预留
        
        return "TODO";
    }
}
