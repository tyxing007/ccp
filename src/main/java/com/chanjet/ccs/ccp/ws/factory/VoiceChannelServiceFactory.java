package com.chanjet.ccs.ccp.ws.factory;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.chanjet.ccs.ccp.base.service.VoiceServiceImpl;
import com.chanjet.ccs.ccp.ws.VoiceChannelService;
import com.chanjet.ccs.ccp.ws.impl.YYVoiceChannelServiceImpl;

/**
 * 2015年12月11日下午4:57:04
 * wugqa@chanjet.com
 */
@Component("voiceChannelServiceFactory")
public class VoiceChannelServiceFactory {
	private static Log logger = LogFactory.getLog(VoiceServiceImpl.class);
	
	private static Map<String,VoiceChannelService> map = new HashMap<String,VoiceChannelService>();
	
	@Resource
	private Map<String, String> voiceParam;//此种方式可以实现热更新
	
	public VoiceChannelService getInstance(){
		String channel = voiceParam.get("yy.channel");
		if(StringUtils.isEmpty(channel)){
			logger.error("zk中缺少yy.channel配置信息");
		}
		if(map.get(channel) != null){
			return map.get(channel);
		}
		if("YYVoiceChannel".equals(channel)){
			int maxTotal = Integer.parseInt(voiceParam.get("yy.maxTotal"));
			VoiceChannelService voiceChannelService = new YYVoiceChannelServiceImpl(maxTotal, voiceParam);
			map.put(channel, voiceChannelService);
			return voiceChannelService;
		}
		return null;
	}
}
