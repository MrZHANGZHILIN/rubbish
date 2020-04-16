package com.llb.service.impl;

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
	 * 查询
	 */
	public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map){
		if (pageSize > 0) {
			map.put("pu", new PageUtil<>(pageNum, pageSize));
		}
		return this.categoryMapper.query(map);
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
