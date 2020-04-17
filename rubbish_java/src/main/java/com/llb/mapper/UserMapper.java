package com.llb.mapper;

import com.llb.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper
 * @Author llb
 * Date on 2020/4/16
 */
public interface UserMapper {
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
     * 根据openid查询用户
     * @param openid
     * @return
     */
    User findByOpenid(String openid);

    /**
     * 通用查询
     * @param nickName
     * @return
     */
    List<Map<String, Object>> find(String nickName);

    /**
     * 查询记录数
     * @param nickName
     * @return
     */
    int totalCount(String nickName);

    /**
     * 分页查询
     */
    List<Map<String, Object>> query(Map<String, Object> map);

    /**
     * 删除
     * @param map
     */
    void delete(Map<String, Object> map);
}
