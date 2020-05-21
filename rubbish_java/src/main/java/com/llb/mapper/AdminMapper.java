package com.llb.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AdminMapper {
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Admin entity);
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Admin entity);
	public Admin findByDbid(@Param("dbid") int dbid);
	public Admin findByLoginId(@Param("loginId") String loginId);
	/**
	 * 查询
	 */
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, @Param("loginId") String loginId);

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map);
}
