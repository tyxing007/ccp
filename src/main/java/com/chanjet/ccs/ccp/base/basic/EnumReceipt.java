package com.chanjet.ccs.ccp.base.basic;


/**
 * 上行的短信是否自动回执
 * @author huangx
 *
 */
public class EnumReceipt {

	public static final Integer  RECEIPT=1;//开启回执
	
	public static final Integer NO_RECEIPT=0;//关闭回执
	
	public static final String SUCCESS_RESULT="0";//调用ccp接口完全成功，将返回0
	
	public static final String SUCCESS="200";//http 正常接收状态
}
