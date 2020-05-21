package com.llb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface QuestionMapper {
	/**
	 * 添加
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
	public Question findByDbid(@Param("dbid") int dbid);
	public Question findByIndex(@Param("index") int index);
	/**
	 * 通用查询
	 */
	public List<Question> find(Map<String, Object> map);
	/**
	 * 查询
	 */
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam,
											@Param("name") String name);

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map);
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map);
	/**
	 * 统计数量
	 */
	public long count(Map<String, Object> map);
}
