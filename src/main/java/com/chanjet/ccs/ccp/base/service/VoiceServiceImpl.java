/**
 * 2015年12月11日下午2:52:04
 * wugqa@chanjet.com
 */
package com.chanjet.ccs.ccp.base.service;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.chanjet.ccs.ccp.base.dao.VoiceDao;
import com.chanjet.ccs.ccp.base.entity.VoiceSms;
import com.chanjet.ccs.ccp.ws.VoiceChannelService;
import com.chanjet.ccs.ccp.ws.factory.VoiceChannelServiceFactory;

/**
 * 2015年12月11日下午2:52:04
 * wugqa@chanjet.com
 */
@Service("voiceService")
public class VoiceServiceImpl implements VoiceService{
	
	private static Log logger = LogFactory.getLog(VoiceServiceImpl.class);
	
	@Resource
	private VoiceDao voiceDao;
	
	@Resource
	private VoiceChannelServiceFactory voiceChannelServiceFactory;

	@Override
	public void saveAndSend(VoiceSms voiceSms) {
		//保存语音信息到数据库
		voiceDao.saveT(voiceSms);
		//获取通道实例，此处采用工厂模式方便以后扩展
		VoiceChannelService voiceChannelService = voiceChannelServiceFactory.getInstance() ;
		if(voiceChannelService == null) {
			logger.error("语音通道信息配置错误,请核查zk的配置");
		}
		voiceChannelService.sendVoice(voiceSms, voiceDao);
	}

}
