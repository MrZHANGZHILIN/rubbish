package com.llb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.User;

import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/16
 */
public interface IUserService {
    /**
     * 插入
     * @param user
     * @return
     */
    public void insert(User user);

    /**
     * 跟新
     * @param user
     */
    void update(User user);
    /**
     * 分页查询
     */
    IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name);
    /**
     * 根据openid查询用户
     * @param openid
     * @return
     */
    User findByOpenid(String openid);

    /**
     * 通用查询
     * @param map
     * @return
     */
    List<Map<String, Object>> find(Map<String, Object> map);

    /**
     * 查询记录数
     * @param map
     * @return
     */
    int totalCount(Map<String, Object> map);

    /**
     * 删除
     * @param map
     */
    void delete(Map<String, Object> map);

    /**
     * 微信授权登录
     * @param code
     * @return
     */
    Map<String, Object> wxLogin(String code);
}
