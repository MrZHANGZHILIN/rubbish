package com.llb.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.hash.Hashing;
import com.llb.common.ResultInfo;
import com.llb.entity.Category;
import com.llb.entity.Rubbish;
import com.llb.service.IBaiduAi;
import com.llb.utils.HttpClientUtil;
import com.llb.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 垃圾分类识别  图片识别  语音识别接口
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
    @Value("${jingdongAppKey}")
    private String jingDongAppKey;
    @Value("${jingdongSecretKey}")
    private String jingDongSecretKey;
    @Value("${garbageTextSearchUrl}")
    private String textSearchUrl;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IBaiduAi baiduAi;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RubbishService rubbishService;
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
     * 京东鉴权信息
     * 请求参数：secretkey和timestamp参数值进行拼装
     *          并且使用MD5对拼装完的字符串进行加密，获取sign
     * @return
     */
    @Override
    public ResultInfo<Object> garbageTextSearch(String garbage) {
        List<Rubbish> rubbishes = new ArrayList<>();
        Map<String, Object> params = new HashMap<String, Object>();
        long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        //获取认证信息
        String sign = Hashing.md5().hashString(jingDongSecretKey + timestamp, Charset.forName("UTF-8")).toString();
        //必须参数
        params.put("text", garbage);
        //这里默认是上海市分类标准
        params.put("cityId", "310000");
        String searchUrl = textSearchUrl + "?appkey=" + jingDongAppKey + "&timestamp=" + timestamp + "&sign=" + sign;
        JSONObject httpResponseJson = new HttpClientUtil().getHttpResponseJson(searchUrl, params);
        //系统级状态码
        String code = httpResponseJson.getString("code");
        if(!"10000".equals(code)) {
            return new ResultInfo(Integer.parseInt(code), httpResponseJson.getString("msg"));
        }
        //业务级状态码
        Integer status = httpResponseJson.getJSONObject("result").getInteger("status");
        //判断调用接口是否错误
        if(status!= 0)
            return new ResultInfo(status, httpResponseJson.getJSONObject("result").getString("message"));
        JSONArray jsonArray = httpResponseJson.getJSONObject("result").getJSONArray("garbage_info");
        //遍历JSONArray
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject garbageInfo = jsonArray.getJSONObject(i);
            int dbid = categoryService.findDbidByName(garbageInfo.getString("cate_name"));
            //将此数据插入到本地数据库中
            Rubbish rubbish = new Rubbish();
            rubbish.setName(garbageInfo.getString("garbage_name"));
            rubbish.setHot("no");
            rubbish.setPs(garbageInfo.getString("ps"));
            rubbish.setCategoryId(dbid);
            rubbishService.insert(rubbish);
            rubbishes.add(rubbish);
        }

        return new ResultInfo<>(status, rubbishes);
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
