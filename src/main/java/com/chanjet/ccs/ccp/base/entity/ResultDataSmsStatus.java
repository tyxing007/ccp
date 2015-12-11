package com.chanjet.ccs.ccp.base.entity;

import com.chanjet.ccs.ccp.base.basic.EnumValues;

public class ResultDataSmsStatus {

    public static String getStatus(int status) {
        
        String info = "不能确定当前短信的发送状态";
        if (status == EnumValues.SMS_FAILED)
            info = "发送失败";
        else if (status == EnumValues.SMS_SENDING)
            info = "正在发送";
        else if (status == EnumValues.SMS_UNSEND)
            info = "尚未发送";
        else if (status == EnumValues.SMS_SENDED)
            info = "发送成功";
        return info;
        
    }
    
}
