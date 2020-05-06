package com.llb.controller;

import com.alibaba.fastjson.JSONObject;
import com.llb.service.IBaiduAi;
import com.llb.service.impl.BaiduAiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度ai
 * @Author llb
 * Date on 2020/4/20
 */
@Controller
@RequestMapping("/BaiduAi")
public class BaiduAiController extends BaseController{

    @Autowired
    private IBaiduAi baiduAi;

    @RequestMapping("/BaiduAi")
    public Map<String, Object> baiduImageAuth() {
        Map<String, Object> result = new HashMap<>();
        try {
             baiduAi.authBaiduImage();
        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;
    }
}
