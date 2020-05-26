package com.llb.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.User;
import com.llb.mapper.UserMapper;
import com.llb.service.IUserService;
import com.llb.utils.HttpClientUtil;
import com.llb.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/16
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 微信用户appid、secret以及授权接口
     */
    private static final String WX_AUTH = PropertiesUtil.getProperty("wx_auth");
    private static final String WX_APPID = PropertiesUtil.getProperty("wx_appid");
    private static final String WX_SECRETKEY = PropertiesUtil.getProperty("wx_secretkey");
    HttpClientUtil httpClientUtil = new HttpClientUtil();
    @Override
    public void insert(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name) {
        return userMapper.query(pageParam, name);
    }

    @Override
    public User findByOpenid(String openid) {
        return userMapper.findByOpenid(openid);
    }

    @Override
    public List<Map<String, Object>> find(Map<String, Object> map) {
        return userMapper.find(map.get("nickName").toString());
    }

    @Override
    public int totalCount(Map<String, Object> map) {
        return userMapper.totalCount(map.get("nickName").toString());
    }

    @Override
    public void delete(Map<String, Object> map) {
        userMapper.delete(map);
    }

    /**
     * 微信授权登录
     * @param code
     * @return
     */
    @Override
    public Map<String, Object> wxLogin(String code) {
        //封装请求参数
        Map<String, Object> params = new HashMap<String, Object>();
        //封装返回结果
        Map<String, Object> result = new HashMap<>();
        //微信授权登录所需要的四个参数
        params.put("appid", WX_APPID);
        params.put("secret", WX_SECRETKEY);
        params.put("js_code", code);
        //固定值
        params.put("grant_type", "authorization_code");
        JSONObject response = httpClientUtil.getResponseByParams(WX_AUTH, params);
        //0：成功 -1：繁忙，稍后再试 40029：code无效 45011：频率限制，每个用户每分钟100次
        String errcode = response.getString("errcode");
        result.put("code", errcode);
        if(errcode == "0") {
            //成功
            result.put("msg", "授权成功");
            result.put("data", response);
        } else {
            //授权失败
            String errmsg = response.getString("errmsg");
            result.put("msg", "errmsg");
            result.put("data", response);
        }
        return result;
    }
}
