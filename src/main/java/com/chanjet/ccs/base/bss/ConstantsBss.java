package com.chanjet.ccs.base.bss;



public class ConstantsBss {
    
    // Bss提供的服务地址
    //public static final String urlCsp = "http://ciacsp.chanjet.com";   
    
	public static final String urlCsp = "http://cia.chanapp.chanjet.com";// Bss's url
    public static final String urlAuth = urlCsp + "/internal_api/authorize";                    // 获取code
    public static final String urlAuthen = urlCsp + "/internal_api/authentication";             // 登录(获取token)
    public static final String urlUserInfo = urlCsp + "/api/v1/user";                           // 查询用户
    public static final String urlAppInfo = urlCsp + "/internal_api/v1/app/appCertification";   // 查询密钥
    
    
    // 请求Bss服务时传递的参数名称（部分也作为返回的属性名称）
    public static final String kAppKey = "appKey";
    public static final String kAppSecret = "appSecret";
    public static final String kAccessToken = "access_token";
    public static final String kOrgAccessToken = "org_access_token";
    public static final String kSearchAppKey = "searchAppKey";
    public static final String kSearchAppSecret = "searchAppSecret";
    
    
    // Bss返回的属性名称
    public static final String kResult= "result";
    public static final String kAppId= "appId";
    public static final String kUserId= "userId";
    public static final String kUserChanjetId= "userChanjetId";
    public static final String kOrgId= "orgId";
    public static final String kIsvId= "isvId";
    public static final String kErrorCode = "error_code";
    
    
    // TODO BSS返回的错误处理
    // Bss返回的错误信息
    public static final String error20106 = "业务异常：用户未找到";
    public static final String error20110 = "业务异常：用户无所属组织";
    public static final String error10001 = "参数异常：格式错误";
    public static final String error10004 = "参数异常：非空参数为空的错误";
    public static final String error50000 = "系统错误：服务器内部错误";
    public static final String error20302 = "业务异常：appKey不存在";
    public static final String error20303 = "业务异常：appKey与appSecret不匹配";
    public static final String error20304 = "业务异常：searchAppKey不存在";
    public static final String error20305 = "业务异常：appKey没有访问权限";
    
    
    
}
