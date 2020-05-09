package com.llb.service;

import com.alibaba.fastjson.JSONObject;
import com.llb.common.ResultInfo;

import java.io.File;

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
    String getBaiduAuth();

    /**
     * 京东鉴权信息
     * @return
     */
    ResultInfo<Object> garbageTextSearch(String garbage);

    /**
     * 图片识别
     * @param image 图片文件
     * @return
     */
    ResultInfo<Object> imageRecognition(File image);
}
