package com.llb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.common.PageUtil;
import com.llb.mapper.CategoryMapper;
import com.llb.entity.Category;
import com.llb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService implements ICategoryService {
	@Autowired
	private CategoryMapper categoryMapper;
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Category entity){
		return this.categoryMapper.insert(entity);
	}
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Category entity){
		return this.categoryMapper.update(entity);
	}
	public Category findByDbid(int dbid){
		return categoryMapper.findByDbid(dbid);
	}
	/**
	 * 通用查询
	 */
	public List<Category> find(Map<String, Object> map){
		return this.categoryMapper.find(map);
	}

	/**
	 * 分类名称查询分类id
	 * @param name
	 * @return
	 */
	@Override
	public int findDbidByName(String name) {
		return this.categoryMapper.findDbidByName(name);
	}

	/**
	 * 查询
	 */
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name){
		return categoryMapper.query(pageParam, name);
	}

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map){
		return this.categoryMapper.totalCount(map);
	}
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map){
		return this.categoryMapper.delete(map);
	}
}
