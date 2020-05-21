package com.llb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Rubbish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/14
 */
public interface IRubbishService {

    /**
     * 插入
     */
    public int insert(Rubbish entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    public int update(Rubbish entity);

    /**
     * 根据dbid查询
     * @param dbid
     * @return
     */
    public Rubbish findByDbid(int dbid);

    /**
     * 根据name查找
     * @param name
     * @return
     */
    public Rubbish findByName(String name);

    /**
     * 通用查询
     * @param map
     * @return
     */
    public List<Rubbish> find(Map<String, Object> map);

    /**
     * 分页查询
     * @param pageNum
     * @param pageSize
     * @param map
     * @return
     */
//    public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map);
    IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name);

    /**
     * 总记录数
     * @param map
     * @return
     */
    public long totalCount(Map<String, Object> map);

    /**
     * 删除
     * @param map
     * @return
     */
    public int delete(Map<String, Object> map);
}
