package com.llb.mapper;

import com.llb.entity.Rubbish;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface RubbishMapper {
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Rubbish entity);
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Rubbish entity);
	public Rubbish findByDbid(@Param("dbid") int dbid);
	public Rubbish findByName(@Param("name") String name);
	/**
	 * 通用查询
	 */
	public List<Rubbish> find(Map<String, Object> map);
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
