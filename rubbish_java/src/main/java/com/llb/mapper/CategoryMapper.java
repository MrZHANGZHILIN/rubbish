package com.llb.mapper;

import com.llb.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CategoryMapper {
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
	public Category findByDbid(@Param("dbid") int dbid);
	/**
	 * 通用查询
	 */
	public List<Category> find(Map<String, Object> map);
	/**
	 * 查询
	 */
	public List<HashMap<String, Object>> query(Map<String, Object> map);

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map);
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map);
}
