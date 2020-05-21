package com.llb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
	 * 分类名称查询分类id
	 * @param name
	 * @return
	 */
	int findDbidByName(String name);
	/**
	 * 查询
	 */
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name);

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map);
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map);
}
