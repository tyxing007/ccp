package com.chanjet.ccs.common.sdk.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.chanjet.ccs.common.sdk.base.CcsCMConstants;
import com.chanjet.ccs.common.sdk.base.CcsCMResult;


public class HttpUtil {
    
    public static String get(String url, String authorization) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.addHeader(CcsCMConstants.kAuthorization, authorization);
        HttpResponse response = httpclient.execute(get);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(instream, CcsCMConstants.UTF8));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            httpclient.getConnectionManager().shutdown();
            return sb.toString();
        }
        return null;
    }
    
    public static String post(String url, List<NameValuePair> params, String authorization) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        if (StringUtils.isNotEmpty(authorization))
            post.addHeader(CcsCMConstants.kAuthorization, authorization);
        HttpResponse response = httpclient.execute(post);
        HttpEntity entity = response.getEntity();
        String s = EntityUtils.toString(entity, CcsCMConstants.GB2312); // TODO
        httpclient.getConnectionManager().shutdown();
        return s;
    }
    
    
    public static Object getResultObj(String url, String authorization) throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.addHeader(CcsCMConstants.kAuthorization, authorization);
        HttpResponse response = httpclient.execute(get);
        HttpEntity entity = response.getEntity();
        if (response.getStatusLine().getStatusCode() == 200) {
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream, CcsCMConstants.UTF8));
                StringBuilder sb = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                httpclient.getConnectionManager().shutdown();
                return sb.toString();
            }
        } else {
            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream, CcsCMConstants.UTF8));
                StringBuilder sb = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {
                    sb.append(str);
                }
                httpclient.getConnectionManager().shutdown();
                return (CcsCMResult)DataWraper.wrapJson2Object(sb.toString(), CcsCMResult.class);
            }
        }
        return null;
    }
    

}
