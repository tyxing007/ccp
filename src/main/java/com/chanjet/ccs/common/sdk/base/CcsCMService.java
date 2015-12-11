package com.chanjet.ccs.common.sdk.base;


public class CcsCMService {


	//服务列表
    public enum service {
    	weather,//天气
    	social,//社交
    	lbs,//地理服务
    	video,//视频
    	meeting,//网络会议
    	twoDcode,//二维码
    	ocr,//文件识别
    	pns,//消息推送
    	ses,//邮件
    	ccp,//短信
    	oss,//在线存储
    	mailFile,//邮件归档
    	voice,//语言识别
    	preview;//文档转换
    
    }
    
    //子服务列表
    public enum subService {
    	weather_weather,//天气
    	social_social,//社交
    	lbs_lbs,//地理服务
    	video_video,//视频
    	meeting_meeting,//网络会议
    	twoDcode_2Dcode,//二维码
    	ocr_ocr,//文字识别
    	pns_push,//消息推送
    	ses_send_Marketing,//营销邮件
    	ses_send_notice,//系统邮件
    	ccp_send_Marketing,//营销短信
    	ccp_send_notice,//系统短信
    	ccp_sendAndRevice,//上行短信
    	oss_oss,//在线存储
    	mailFile_file,//邮件归档
    	voice_voice,//语言识别
    	preview_convert;//文档转换
    	
    }
    //维度
    public enum dimensions {
    
    	space,//空间
    	time,//时间
    	flow,//流量
    	member;//数量
        
    }
    
    
    
/**
 * 在充值的时候需要单位，扣费的时候安裝最小的數據單位進行扣費。
 * @author huangx
 *
 */
    public enum unit {
    	G,//空间、流量，G
    	MB,//MB
    	KB,//KB
    	B,//B
    	hour,//时长，小时
    	minute,//时长，分钟
    	S,//时长，秒
    	M;//次数
    }
    
    
    
    
    public static void main(String[] args) {
		System.out.println("ccp".equals(service.ccp.toString()));
		unit s = unit.G;
		unit.valueOf("H");
		System.out.println(s.toString());
	}
    
}
