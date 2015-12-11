package com.chanjet.ccs.base.bss;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.chanjet.ccs.base.basic.ConstantsCcs;
import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.base.util.DataWraper;
import com.chanjet.ccs.base.util.HttpUtil;
import com.chanjet.ccs.base.util.MD5;
import com.chanjet.ccs.base.util.ReqRespHelper;
import com.chanjet.ccs.ccp.base.basic.EnumResult;

@SuppressWarnings("deprecation")
public class BssService {
    
    private static Log logger = LogFactory.getLog(BssService.class);
    
    
    //{"result":true,"resultKey":"200::Success::请求成功","data":"{\"access_token\":\"2ee5101f-3bd8-4176-8a04-a1ddc0abc2d2\",\"auth_result\":\"true\",\"expires_in\":\"629999\",\"refresh_token\":\"7409d344-5254-4e86-a5ab-bdfe5fcf9bd1\"}"}
    public static ResultInner<String> getTestToken(String username, String password) {
        
        MD5 md5 = new MD5();
        password = md5.getMD5ofStr(password).toLowerCase();
        String client_id = "commonservice";
        String state = "xxsss";
        String redirect_uri = "http://www.baidu.com";
        
        Map<String, String> params = new HashMap<String, String>();
        params.put("client_id", client_id);
        params.put("state", state);
        params.put("redirect_uri", redirect_uri);
        String paramsString = ReqRespHelper.getParamsString(params);
        String getCodeUrl = ConstantsBss.urlAuth + paramsString;
        
        ResultInner<String> result = HttpUtil.httpGetNoRedirect(getCodeUrl);
        logger.info(username + ", " + password + " => " + result);
        
        List<NameValuePair> nparams = new ArrayList<NameValuePair>();
        nparams.add(new BasicNameValuePair("client_id", client_id));
        nparams.add(new BasicNameValuePair("auth_username", username));
        nparams.add(new BasicNameValuePair("password", password));
        nparams.add(new BasicNameValuePair("auth_code", result.getData()));
        logger.info(client_id + ", " + username + ", " + password + ", " + result.getData());
        ResultInner<String> nresult = HttpUtil.httpPost(ConstantsBss.urlAuthen, nparams);
        logger.info(username + ", " + password + " => " + nresult);
        if (nresult.getResult()) {
            Map<String, String> data = DataWraper.wrapJson2MapStr(nresult.getData());
            if ("true".equals(data.get("auth_result"))) {
                return new ResultInner<String>(nresult.getResultKey(), data.get("access_token"));
            } else 
                return new ResultInner<String>(EnumResult.bssReturnError);
        } else {
            return new ResultInner<String>(nresult.getResultKey());
        }
    }
    
    
    public static ResultInner<HashMap<String, String>> getUserInfo(String accessToken) {
        return getUserInfo(accessToken, null);
    }
    
    // {\"defaultOrganization\":\"fb238bc0-17ca-40cc-a66e-0d0e1806183b\",\"email\":\"huangxiong1@1633.com\",\"mobile\":\"18110886637\",\"name\":\"huangxiong1\",\"nickName\":\"huangxiong1\",\"orgId\":\"fb238bc0-17ca-40cc-a66e-0d0e1806183b\",\"origin\":\"csp平台\",\"result\":true,\"sex\":\"\",\"userChanjetId\":\"10109\",\"userId\":\"4855199b-f6e8-49b1-a2bd-e5426b38e7a6\",\"username\":\"huangxiong1\"}"
    public static ResultInner<HashMap<String, String>> getUserInfo(String accessToken, String orgAccessToken) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put(ConstantsBss.kAppKey, ConstantsCcs.vAppKey);
        params.put(ConstantsBss.kAccessToken, accessToken);
        if (StringUtils.isNotEmpty(orgAccessToken))
            params.put(ConstantsBss.kOrgAccessToken, orgAccessToken);
        String paramsString = ReqRespHelper.getParamsString(params);
        String url = ConstantsBss.urlUserInfo + paramsString;
        ResultInner<String> result = HttpUtil.httpGet(url);
        //String s = "{\"defaultOrganization\":\"c68d8962-fb95-426d-ac95-9b39928a7c03\",\"email\":\"huangxiong@1633.com\",\"mobile\":\"18110886637\",\"name\":\"huangxiong\",\"nickName\":\"huangxiong\",\"orgId\":\"c68d8962-fb95-426d-ac95-9b39928a7c03\",\"orgType\":1,\"origin\":\"csp平台\",\"result\":true,\"sex\":\"\",\"userChanjetId\":\"10110\",\"userId\":\"6b3c3317-8fec-4ae8-b480-8391a2e0c84f\",\"username\":\"huangxiong\"}";
        //ResultInner<String> result = new ResultInner<String>(EnumResult.uniSuccess, s);
        logger.info(accessToken + " => " + result);
        if (result.getResult()) {
            Map<String, Object> map = DataWraper.wrapJson2MapObj(result.getData());
            if (map.get(ConstantsBss.kResult) != null && (Boolean)map.get(ConstantsBss.kResult)) {
                HashMap<String, String> userInfo = new HashMap<String, String>();
                userInfo.put(ConstantsCcs.kBssOrgId, (String)map.get(ConstantsBss.kOrgId));
                userInfo.put(ConstantsCcs.kBssUserId, (String)map.get(ConstantsBss.kUserId));
                return new ResultInner<HashMap<String, String>>(EnumResult.uniSuccess , userInfo);
            } else {
                return new ResultInner<HashMap<String, String>>(EnumResult.bssReturnError);
            }
        }
        return new ResultInner<HashMap<String, String>>(result.getResultKey());
    }
    
    
    // data":"{\"appId\":\"\",\"isvId\":\"c4079a34-506e-4e32-8cb2-41535a288fde\",\"isvName\":\"畅捷通\",\"result\":true,\"searchAppKey\":\"c4079a34-506e-4e32-8cb2-41535a288fde\",\"searchAppSecret\":\"uioerw\"}"}
    public static ResultInner<HashMap<String, String>> getAppCertification(String searchAppKey) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(ConstantsBss.kAppKey, ConstantsCcs.vAppKey);
        params.put(ConstantsBss.kAppSecret, ConstantsCcs.vAppSecret);
        params.put(ConstantsBss.kSearchAppKey, searchAppKey);
        String paramsString = ReqRespHelper.getParamsString(params);
        String url = ConstantsBss.urlAppInfo + paramsString;
        ResultInner<String> result = HttpUtil.httpGet(url);
        //String s = "{\"appId\":\"\",\"isvId\":\"13824ca1-7c7b-41b7-bcaa-18a4c8e8e66b\",\"isvName\":\"畅捷通\",\"result\":true,\"searchAppKey\":\"13824ca1-7c7b-41b7-bcaa-18a4c8e8e66b\",\"searchAppSecret\":\"ydfeme\"}";
        //ResultInner<String> result =new ResultInner<String>(EnumResult.uniSuccess, s);
        logger.info(searchAppKey + " => " + result);
        if (result.getResult()&&!StringUtils.isEmpty(result.getData())) {
            HashMap<String, Object> map = DataWraper.wrapJson2MapObj(result.getData());
            if (map.get(ConstantsBss.kResult) != null && (Boolean)map.get(ConstantsBss.kResult)) {
                HashMap<String, String> appInfo = new HashMap<String, String>();
                appInfo.put(ConstantsCcs.kBssAppId, (String)map.get(ConstantsBss.kAppId));
                appInfo.put(ConstantsCcs.kBssAppKey, (String)map.get(ConstantsBss.kSearchAppKey));
                appInfo.put(ConstantsCcs.kBssAppSecret, (String)map.get(ConstantsBss.kSearchAppSecret));
                appInfo.put(ConstantsCcs.kBssIsvId, (String)map.get(ConstantsBss.kIsvId));
                return new ResultInner<HashMap<String, String>>(EnumResult.uniSuccess , appInfo);
            } else {
                return new ResultInner<HashMap<String, String>>(EnumResult.bssReturnError);
            }
        }
        return new ResultInner<HashMap<String, String>>(result.getResultKey());
    }
    
    
    

    /*
    public static String requestHttpsGet(String url) throws ClientProtocolException, IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpget);
        System.out.println(response.getStatusLine());
        HttpEntity entity = response.getEntity();
        // If the response does not enclose an entity, there is no need to worry about connection release
        if (entity != null) {
            InputStream instream = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                // do something useful with the response
                System.out.println(reader.readLine());
            } catch (IOException ex) {
                // In case of an IOException the connection will be released back to the connection manager automatically
                throw ex;
            } catch (RuntimeException ex) {
                // In case of an unexpected exception you may want to abort the HTTP request in order to shut down the underlying
                // connection and release it back to the connection manager.
                httpget.abort();
                throw ex;
            } finally {
                // Closing the input stream will trigger connection release
                instream.close();

            }
            httpclient.getConnectionManager().shutdown();
            return "";
        }
        return "";
    }*/
    
    public static String reqestHttpsPost(String url) throws NoSuchAlgorithmException, KeyManagementException, ClientProtocolException, IOException {
        Map<String, String> params = new HashMap<String, String>(); 
        params.put("TransName", "IQSR"); 
        params.put("Plain", "transId=IQSR~|~originalorderId=2012~|~originalTransAmt= ~|~merURL= "); 
        params.put("Signature", "9b759887e6ca9d4c24509d22ee4d22494d0dd2dfbdbeaab3545c1acee62eec7"); 
        
        @SuppressWarnings("unused")
		long responseLength = 0;                         //响应长度 
        String responseContent = null;                   //响应内容 
        @SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
        
        X509TrustManager xtm = new X509TrustManager(){   //创建TrustManager 
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                
            } 
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                
            }
            public X509Certificate[] getAcceptedIssuers() { 
                return null; 
            }
            
        }; 
        //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
        SSLContext ctx = SSLContext.getInstance("TLS"); 
        //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
        ctx.init(null, new TrustManager[]{xtm}, null); 
        //创建SSLSocketFactory 
        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx); 
        //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
        HttpPost httpPost = new HttpPost("https://www.cebbank.com/per/QueryMerchantEpay.do"); //创建HttpPost 
        
        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数 
        for(Map.Entry<String,String> entry : params.entrySet()){ 
            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
        } 
        httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); 
         
        HttpResponse response = httpClient.execute(httpPost); //执行POST请求 
        HttpEntity entity = response.getEntity();             //获取响应实体 
         
        if (null != entity) { 
            responseLength = entity.getContentLength(); 
            responseContent = EntityUtils.toString(entity, "UTF-8"); 
            EntityUtils.consume(entity); //Consume response content 
        } 
//        System.out.println("请求地址: " + httpPost.getURI()); 
//        System.out.println("响应状态: " + response.getStatusLine()); 
//        System.out.println("响应长度: " + responseLength); 
//        System.out.println("响应内容: " + responseContent); 
        httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源 
        return responseContent; 
    }
    
    
    public static String getUserInfox(String appKey, String accessToken) {
        try {
            Map<String, String> params = new HashMap<String, String>(); 
            params.put("TransName", "IQSR"); 
            params.put("Plain", "transId=IQSR~|~originalorderId=2012~|~originalTransAmt= ~|~merURL= "); 
            params.put("Signature", "9b759887e6ca9d4c24509d22ee4d22494d0dd2dfbdbeaab3545c1acee62eec7"); 
            
            @SuppressWarnings("unused")
			long responseLength = 0;                         //响应长度 
            String responseContent = null;                   //响应内容 
            @SuppressWarnings("resource")
			HttpClient httpClient = new DefaultHttpClient(); //创建默认的httpClient实例
            
            X509TrustManager xtm = new X509TrustManager(){   //创建TrustManager 
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    
                } 
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    
                }
                public X509Certificate[] getAcceptedIssuers() { 
                    return null; 
                }
                
            }; 
            //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
            SSLContext ctx = SSLContext.getInstance("TLS"); 
            //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
            ctx.init(null, new TrustManager[]{xtm}, null); 
            //创建SSLSocketFactory 
            SSLSocketFactory socketFactory = new SSLSocketFactory(ctx); 
            //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
            httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));
            HttpPost httpPost = new HttpPost("https://www.cebbank.com/per/QueryMerchantEpay.do"); //创建HttpPost 
            
            List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //构建POST请求的表单参数 
            for(Map.Entry<String,String> entry : params.entrySet()){ 
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue())); 
            } 
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8")); 
             
            HttpResponse response = httpClient.execute(httpPost); //执行POST请求 
            HttpEntity entity = response.getEntity();             //获取响应实体 
             
            if (null != entity) { 
                responseLength = entity.getContentLength(); 
                responseContent = EntityUtils.toString(entity, "UTF-8"); 
                EntityUtils.consume(entity); //Consume response content 
            } 
//            System.out.println("请求地址: " + httpPost.getURI()); 
//            System.out.println("响应状态: " + response.getStatusLine()); 
//            System.out.println("响应长度: " + responseLength); 
//            System.out.println("响应内容: " + responseContent); 
            httpClient.getConnectionManager().shutdown(); //关闭连接,释放资源 
            return responseContent; 
        } catch (Exception ex) {
            logger.error("", ex);
        }
        return null;
        
        
        //return null;
    }

    public static String getAppInfo(String searchAppKey) {

        return null;
    }

    @SuppressWarnings("unused")
	private static String _request(String params) {

        return null;
    }

    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, ClientProtocolException, IOException {
        getUserInfo(null, null);
    }
}
