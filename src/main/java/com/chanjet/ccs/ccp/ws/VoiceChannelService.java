package com.chanjet.ccs.ccp.ws;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.chanjet.ccs.ccp.base.dao.VoiceDao;
import com.chanjet.ccs.ccp.base.entity.VoiceSms;

/**
 * 2015年12月11日下午3:03:16
 * wugqa@chanjet.com
 */
public abstract class VoiceChannelService {
	
	private static Log logger = LogFactory.getLog(VoiceChannelService.class);
	
	public void sendVoice(VoiceSms voiceSms, VoiceDao voiceDao){
		String mobiles = voiceSms.getMobiles();
		String content = voiceSms.getContent();
		String[] mobile = mobiles.split(",");
		if(mobile != null && mobile.length > 0){
			sendVoice(voiceSms,mobile[0],content,voiceDao);
		}else{
			logger.error("mobiles is empty" + mobiles);
		}
	}
	public abstract void sendVoice(VoiceSms voiceSms,String mobileNo,String captcha,VoiceDao voiceDao);
}
