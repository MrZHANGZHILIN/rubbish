package com.llb.service.impl;

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
	public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map){
		if (pageSize > 0) {
			map.put("pu", new PageUtil<>(pageNum, pageSize));
		}
		return this.adminMapper.query(map);
	}

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map){
		return this.adminMapper.totalCount(map);
	}
}
