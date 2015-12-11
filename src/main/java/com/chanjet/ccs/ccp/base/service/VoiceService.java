package com.chanjet.ccs.ccp.base.service;

import com.chanjet.ccs.ccp.base.entity.VoiceSms;

/**
 * 2015年12月11日下午2:45:46
 * wugqa@chanjet.com
 */
public interface VoiceService {
	/**
	 * 目前只支持发送验证码
	 * @param voiceSms 	保存语音信息并且调用语音通道进行发送
	 */
	public void saveAndSend(VoiceSms voiceSms);
}
