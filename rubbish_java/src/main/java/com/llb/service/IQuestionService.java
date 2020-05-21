package com.llb.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author llb
 * Date on 2020/4/14
 */
public interface IQuestionService {

    /**
     * 插入
     * @param entity
     * @return
     */
    public int insert(Question entity);

    /**
     * 修改
     * @param entity
     * @return
     */
    public int update(Question entity);

    /**
     * 根据dbid查询
     * @param dbid
     * @return
     */
    public Question findByDbid(int dbid);

    /**
     * 根据index查询
     * @param index
     * @return
     */
    public Question findByIndex(int index);

    /**
     * 通用查询
     * @param map
     * @return
     */
    public List<Question> find(Map<String, Object> map);

    /**
     * 分页查询
     * @return
     */
    public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam,
                                            String name);

    /**
     * 查询总记录数
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

    /**
     * 统计数量
     * @param map
     * @return
     */
    public long count(Map<String, Object> map);
}
