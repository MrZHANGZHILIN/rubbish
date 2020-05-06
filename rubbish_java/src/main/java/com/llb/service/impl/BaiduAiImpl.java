package com.llb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.llb.service.IBaiduAi;
import com.llb.utils.HttpClientUtil;
import com.llb.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @Author llb
 * Date on 2020/4/20
 */
@Service
public class BaiduAiImpl implements IBaiduAi {

    //图片识别
    //apikey
    private static final String BaiduImageApiKey = PropertiesUtil.getProperty("baiduImageApiKey");
    //secretkey
    private static final String BaiduImageSecretKey = PropertiesUtil.getProperty("baiduImageSecretKey");
    //baiduImageAuthApi
    private static final String BaiduImageAuthApi = PropertiesUtil.getProperty("baiduImageAuthApi");
    //baiduImageURL
    private static final String BaiduImageURL = PropertiesUtil.getProperty("baiduImageURL");
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IBaiduAi baiduAi;

    /**
     * 百度图片识别鉴权信息。
     * 为了避免重复鉴权导致异常，这里将获取到的access_token放到了redis中，过期时间为expires_in。
     * access_token每过一段时间刷新一次
     * @return
     */
    @Override
    public void authBaiduImage() {
        Map<String, Object> params = new HashMap<>();
        //必须
        params.put("grant_type", "client_credentials");
        params.put("client_id", BaiduImageApiKey);
        params.put("client_secret", BaiduImageSecretKey);
        //鉴权
        JSONObject responseByParams = new HttpClientUtil().getResponseByParams(BaiduImageAuthApi, params);
        //将accessToken放到redis中定时
        redisTemplate.opsForValue().set("baiduAccess_token", responseByParams.getString("access_token"),
                responseByParams.getLong("expires_in"), TimeUnit.SECONDS);
        //expires_in：expires_in  Access Token的有效期(秒为单位，一般为1个月)；
//        Boolean accessToken = redisTemplate.hasKey("baiduAccess_token");
//        if (!accessToken) {
//
//        }
    }

    /**
     * 图片识别
     * @param imageBase64 识别物体名称Base64编码
     * @return
     */
    @Override
    public JSONObject imageRecognition(String imageBase64, String accessToken) {
        Map<String, Object> params = new HashMap<>();
        params.put("image", imageBase64);
        //查看redis有没有缓存access_token
        Boolean baiduAccess_token = redisTemplate.hasKey("baiduAccess_token");
        if (!baiduAccess_token) {
            //重新缓存access_token
            baiduAi.authBaiduImage();
        }
        params.put("access_token",redisTemplate.opsForValue().get("baiduAccess_token"));
        JSONObject responseByParams = new HttpClientUtil().getResponseByParams(BaiduImageURL, params);
        return responseByParams;
    }

}
