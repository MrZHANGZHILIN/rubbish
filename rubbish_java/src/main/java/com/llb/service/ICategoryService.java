package com.llb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/14
 */
public interface ICategoryService {

    /**
     * 添加
     * @param entity
     * @return
     */
    public int insert(Category entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    public int update(Category entity);

    /**
     * 根据dbid查找
     * @param dbid
     * @return
     */
    public Category findByDbid(int dbid);

    /**
     * 通用查询
     * @param map
     * @return
     */
    public List<Category> find(Map<String, Object> map);

    /**
     * 分类名称查询分类id
     * @param name
     * @return
     */
    public int findDbidByName(String name);

    /**
     * 分页查询
     * @return
     */
    public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name);

    /**
     * 获取记录数
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
