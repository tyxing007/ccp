package com.chanjet.ccs.base.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.chanjet.ccs.base.entity.ResultInner;
import com.chanjet.ccs.ccp.base.basic.EnumResult;


// TODO 检查到底怎么用httpclient
public class HttpUtil {
    
    private static Log logger = LogFactory.getLog(HttpUtil.class);
    
    private static String info = "访问失败，URL = ";
    
    public static ResultInner<String> httpGetNoRedirect(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.getParams().setParameter(ClientPNames.HANDLE_REDIRECTS, false);
        try {
            
            HttpResponse response = httpclient.execute(httpget);
            Header[] headers = response.getHeaders("Location");
            if (headers.length > 0) {
                Header header = headers[0];
                String location = header.getValue();
                System.out.println(location);
                String s = "auth_code=";
                int start = location.indexOf(s);
                int end = location.indexOf("&");
                String code = location.substring(start + s.length(), end);
                httpclient.getConnectionManager().shutdown();
                return new ResultInner<String>(EnumResult.uniSuccess, code);
            }
            return new ResultInner<String>(EnumResult.bssAccessOccurError);
        } catch (Exception ex) {
            logger.error(info + url, ex);
            System.out.println(ex);
            return new ResultInner<String>(EnumResult.bssAccessOccurError);
        }
    }
    
    public static ResultInner<String> httpGet(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            logger.info("s:"+s);
            httpclient.getConnectionManager().shutdown();
            return new ResultInner<String>(EnumResult.uniSuccess, s);
        } catch (Exception ex) {
            logger.error(info + url, ex);
            System.out.println(ex);
            return new ResultInner<String>(EnumResult.bssAccessOccurError);
        }
        
    }
    
    
    public static ResultInner<String> httpPost(String url, List<NameValuePair> params) {
        
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        try {
            post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            String s = EntityUtils.toString(entity, "utf-8");
            httpclient.getConnectionManager().shutdown();
            return new ResultInner<String>(EnumResult.uniSuccess, s);
        } catch (Exception ex) {
            logger.error(info + url, ex);
            return new ResultInner<String>(EnumResult.bssAccessOccurError);
        }
    }
    

    // TODO 
    public static ResultInner<String> httpsGet(String url) throws ClientProtocolException, IOException {
       
        return null;
    }
    
    // TODO 
    public static ResultInner<String> httpsPost(String url) {
        return null;
    }
    
    public static void main(String[] args) {
        String url = "http://www.baidu.com";
        System.out.println(httpGet(url));
    }
    
}
