package com.chanjet.ccs.ccp.ws.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.chanjet.ccs.ccp.base.basic.EnumValues;
import com.chanjet.ccs.ccp.base.dao.VoiceDao;
import com.chanjet.ccs.ccp.base.entity.VoiceSms;
import com.chanjet.ccs.ccp.ws.VoiceChannelService;

/**
 * 2015年12月11日下午3:04:44
 * wugqa@chanjet.com
 */
public class YYVoiceChannelServiceImpl extends VoiceChannelService{
	 
    private static Log logger = LogFactory.getLog(YYVoiceChannelServiceImpl.class);
    
	private CloseableHttpAsyncClient httpAsyncClient ;
	
	private Map<String, String> voiceParam;
	
	public YYVoiceChannelServiceImpl(int maxTotal,Map<String, String> voiceParam){
		this.voiceParam = voiceParam;
		ConnectingIOReactor ioReactor = null;
		try {
			ioReactor = new DefaultConnectingIOReactor();
			PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
	        cm.setMaxTotal(maxTotal);
	        httpAsyncClient = HttpAsyncClients.custom().setConnectionManager(cm).build();
	        httpAsyncClient.start();
		} catch (IOReactorException e) {
			e.printStackTrace();
		}
	}
	
	public void sendVoice(final VoiceSms voiceSms, final String mobileNo, final String captcha, final VoiceDao voiceDao){
		try {
			String uri = "http://111.206.219.17/c-pt/pt/interface.php?"
	        		+ "username="+voiceParam.get("yy.username")+"&password="+voiceParam.get("yy.password")+"&action=captcha&called="+mobileNo+"&"
	        		+ "callid=01057600773&captcha="+captcha;
	        final CountDownLatch latch = new CountDownLatch(1);
	        final HttpGet httpget = new HttpGet(uri);
            httpAsyncClient.execute(httpget, new FutureCallback<HttpResponse>() {
                public void completed(final HttpResponse response) {
                    latch.countDown();
                    HttpEntity entity = response.getEntity();
                    try {
                    	String line;
                    	StringBuffer sb = new StringBuffer();
						BufferedReader in = null;
						in = new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
						while ((line = in.readLine()) != null) {
							sb.append(line);
						}
						in.close();
						String result = sb.toString();
						logger.info("回应的信息为：" + result);
						Document document = DocumentHelper.parseText(result);
						Element root = document.getRootElement();
						String code = root.element("code").getStringValue();
						if("1".equals(code)){
							voiceSms.setState(EnumValues.SMS_SENDED);
							voiceSms.setSendedTime(new Date());
							voiceDao.updateT(voiceSms);
						}else{
							String errorInfo = root.element("tip").getStringValue();
							dealFail(voiceSms,voiceDao,errorInfo,mobileNo,captcha);
						}
					} catch (UnsupportedOperationException | IOException e) {
						e.printStackTrace();
					} catch (DocumentException e) {
						e.printStackTrace();
					}
                }
                public void failed(final Exception ex) {
                    latch.countDown();
                    logger.error(ex);
                    String errorInfo = "连接通道失败";
                    dealFail(voiceSms,voiceDao,errorInfo,mobileNo,captcha);
                }
                public void cancelled() {
                    latch.countDown();
                    String errorInfo = "连接通道被取消";
                    dealFail(voiceSms,voiceDao,errorInfo,mobileNo,captcha);
                   
                }
            });
	        latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void dealFail(VoiceSms voiceSms,VoiceDao voiceDao,String errorInfo, String mobileNo, String captcha){
		 voiceSms.setState(EnumValues.SMS_FAILED);
		 voiceSms.setSendedTime(new Date());
		 voiceSms.setErrorInfo(errorInfo);
		 voiceDao.updateT(voiceSms);
		 logger.error("验证码发送失败：mobileNo is " + mobileNo + "; captcha is " + captcha + "errorinfo is " + errorInfo);
	}
}
