package com.llb.service;

import com.alibaba.fastjson.JSONObject;

/**
 * 百度接口业务层
 * @Author llb
 * Date on 2020/4/20
 */
public interface IBaiduAi {

    /**
     * 百度图片识别鉴权信息
     * @return
     */
    void authBaiduImage();

    /**
     * 图片识别
     * @param imageBase64 识别物体名称Base64编码
     * @return
     */
    JSONObject imageRecognition(String imageBase64, String accessToken);
}
