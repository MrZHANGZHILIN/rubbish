package com.llb.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.common.PageUtil;
import com.llb.mapper.AdminMapper;
import com.llb.entity.Admin;
import com.llb.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService implements IAdminService {
	@Autowired
    private AdminMapper adminMapper;
	
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Admin entity){
		return this.adminMapper.insert(entity);
	}
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Admin entity){
		return this.adminMapper.update(entity);
	}
	public Admin findByDbid(int dbid){
		return adminMapper.findByDbid(dbid);
	}
	/**
	 * 根据账号查找
	 * @param categoryId
	 * @return
	 */
	public Admin findByLoginId(String loginId){
		return adminMapper.findByLoginId(loginId);
	}
	/**
	 * 查询
	 */
	public IPage<Map<String, Object>> query(Page<Map<String, Object>> pageParam, String loginId){
		return adminMapper.query(pageParam, loginId);
	}

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map){
		return this.adminMapper.totalCount(map);
	}
}
