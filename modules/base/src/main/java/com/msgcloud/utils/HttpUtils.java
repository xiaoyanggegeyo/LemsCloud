package com.msgcloud.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.elementspeed.common.env.EnvironmentVars;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpUtils {
	
	public static String httpPost(String url) {
		return httpPost(url, null);
	}
	
	/** 
     * 发送 post请求简易版
     */  
    public static String httpPost(String url, JSONObject jsonParam) {
        CloseableHttpClient httpClient = HttpClients.createDefault(); 
        HttpPost httPost = new HttpPost(url);  
        try {
        	if (null != jsonParam) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httPost.setEntity(entity);
            }
            CloseableHttpResponse response = httpClient.execute(httPost);  
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK ){
//            	System.out.println(url + " " + response.getStatusLine().getStatusCode());
            	return null;
            }
            String responseContent = getResponseContent(response); 
            return responseContent;
        } catch (IOException e) {
			e.printStackTrace();
			return null;
        } finally {  
            try {  
            	httpClient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }

	private static String getResponseContent(CloseableHttpResponse response) throws IOException {
		HttpEntity entity = (HttpEntity) response.getEntity();  
		if (entity != null) {  
		    // 打印响应内容    
			String data = EntityUtils.toString(entity);
//		    System.out.println("Response content: " + data);
		    return data;
		}
		return "";
	}
  
    /** 
     * 发送 get请求 
     */  
    public static String httpSimpleGet(String url) {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
        try {  
            HttpGet httpget = new HttpGet(url);
            
            CloseableHttpResponse response = httpclient.execute(httpget);  
            if(response.getStatusLine().getStatusCode() != 200){  
//            	System.out.println(url + " " + response.getStatusLine().getStatusCode());
            	throw new RuntimeException("http client execute error for " + url);
            }
            String responseContent = getResponseContent(response); 
            if(StringUtil.isEmpty(responseContent)){
//            	System.out.println(responseContent);
            }
            return responseContent;
        }catch (IOException e) {  
            e.printStackTrace();  
            return null;
        }finally {  
            try {  
                httpclient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }  
    /** 
     * 发送 post请求
     */  
    public static String httpPost2(String url, Map<String, Object> paramMap) {
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (paramMap != null) {
			for (Entry<String, Object> entry : paramMap.entrySet()) {
				params.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
		}    	 
        CloseableHttpClient httpClient = HttpClients.createDefault(); 
        HttpPost httPost = new HttpPost(url);  
        try {
        	if (ContainerUtil.isNotEmpty(params)) {
        		httPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));  
            }
            CloseableHttpResponse response = httpClient.execute(httPost);  
            int statusCode = response.getStatusLine().getStatusCode();
            if(statusCode != HttpStatus.SC_OK ){
//            	System.out.println(url + " " + response.getStatusLine().getStatusCode());
            	return null;
            }
            String responseContent = getResponseContent(response); 
            return responseContent;
        } catch (IOException e) {
			e.printStackTrace();
			return null;
        } finally {  
            try {  
            	httpClient.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }

    /**
     * @Title: isAjaxRequest
     * @Description: TODO(判断是否为AJAX请求)
     * @author masn
     * @date 2018年1月23日 下午2:03:05
     * @return boolean  返回类型
     * @throws
     */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		Assert.notNull(request);
		return StringUtils.equalsIgnoreCase(request.getHeader("X-Requested-With"), "XMLHttpRequest");
	}

	/**
	 * 
	 * @param url
	 * @param json
	 */
	public static void restPost(String url, JSONObject json) {
		RestTemplate rest = new RestTemplate();
		rest.postForObject(getURL(url), json,String.class);
	}
	
	public static String getURL(String url){
//		return EnvironmentVars.getServicePath() + url + "?security_id=-1";
		return "http://localhost:80/demo/" + url + "?security_id=-1";
	}
}
