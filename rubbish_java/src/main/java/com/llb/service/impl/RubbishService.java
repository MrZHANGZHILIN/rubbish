package com.llb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.common.PageUtil;
import com.llb.mapper.RubbishMapper;
import com.llb.entity.Rubbish;
import com.llb.service.IRubbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RubbishService implements IRubbishService {
	@Autowired
	private RubbishMapper rubbishMapper;
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Rubbish entity){
		return this.rubbishMapper.insert(entity);
	}
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Rubbish entity){
		return this.rubbishMapper.update(entity);
	}
	public Rubbish findByDbid(int dbid){
		return rubbishMapper.findByDbid(dbid);
	}
	public Rubbish findByName(String name){
		return rubbishMapper.findByName(name);
	}
	/**
	 * 通用查询
	 */
	public List<Rubbish> find(Map<String, Object> map){
		return this.rubbishMapper.find(map);
	}

	/**
	 * 查询
	 */
//	public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map){
//		if (pageSize > 0) {
//			map.put("pu", new PageUtil<>(pageNum, pageSize));
//		}
//		return this.rubbishMapper.query(map);
//	}
	@Override
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String name) {
		return rubbishMapper.query(pageParam, name);
	}


	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map){
		return this.rubbishMapper.totalCount(map);
	}
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map){
		return this.rubbishMapper.delete(map);
	}
}
