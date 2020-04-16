package com.llb.service;

import com.llb.entity.Admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/14
 */
public interface IAdminService {

    /**
     * 添加
     * @param entity
     * @return
     */
    public int insert(Admin entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    public int update(Admin entity);

    /**
     * 根据Dbid查找
     * @param dbid
     * @return
     */
    public Admin findByDbid(int dbid);

    /**
     * 根据loginId查找
     * @param loginId
     * @return
     */
    public Admin findByLoginId(String loginId);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
    public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map);

    /**
     * 获取记录数
     * @param map
     * @return
     */
    public long totalCount(Map<String, Object> map);
}
