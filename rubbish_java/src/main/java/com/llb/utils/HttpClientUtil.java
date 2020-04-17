package com.llb.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/17
 */
public class HttpClientUtil {


    /**
     * 发送post请求。请求参数为key=value&key=value
     * @param url
     * @param params
     * @return
     */
    public JSONObject getResponseByParams(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        //请求发起客户端
        httpClient = HttpClients.createDefault();
        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, String.valueOf(params.get(key))));
            }
            //这里就是：username=kylin&password=123456
            post.setEntity(new UrlEncodedFormEntity(nameValuePairList,"UTF-8"));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            //发送请求并拿到结果
            response = httpClient.execute(post);
            HttpEntity valueEntity = response.getEntity();
            String content = EntityUtils.toString(valueEntity);
            jsonObject = JSONObject.parseObject(content);
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发送post请求。请求参数为json格式
     * @param url
     * @param params
     * @return
     */
    public JSONObject getHttpResponseJson(String url, Map<String, Object> params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = new JSONObject(params);
        try {
            //请求发起客户端
            httpClient = HttpClients.createDefault();
            //通过post方式访问
            HttpPost post = new HttpPost(url);
            // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
            post.setHeader("Content-Type", "application/json;charset=utf8");
            StringEntity paramEntity = new StringEntity(jsonObject.toJSONString(), "UTF-8");
            paramEntity.setContentType("text/json");
            post.setEntity(paramEntity);
            response = httpClient.execute(post);
            HttpEntity valueEntity = response.getEntity();
            String content = EntityUtils.toString(valueEntity);
            jsonObject = JSONObject.parseObject(content);
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 发送post请求。请求参数为json格式
     * @param url
     * @param params
     * @return
     */
    public JSONObject getHttpJson(String url, String params) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        JSONObject jsonObject = null;
        try {
            //请求发起客户端
            httpClient = HttpClients.createDefault();
            //通过post方式访问
            HttpPost post = new HttpPost(url);
            // 设置ContentType(注:如果只是传普通参数的话,ContentType不一定非要用application/json)
            post.setHeader("Content-Type", "application/json;charset=utf8");
            StringEntity paramEntity = new StringEntity(params, "UTF-8");
            post.setEntity(paramEntity);
            response = httpClient.execute(post);
            HttpEntity valueEntity = response.getEntity();
            String content = EntityUtils.toString(valueEntity);
            jsonObject = JSONObject.parseObject(content);
            return jsonObject;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
