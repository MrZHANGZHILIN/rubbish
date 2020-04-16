package com.llb.controller;

import com.llb.common.MD5Util;
import com.llb.service.IAdminService;
import com.llb.entity.Admin;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员管理
 * @author llb
 *
 */
@Controller("adminController")
@RequestMapping("/admin")
public class AdminController extends BaseController{
	private static final Logger L = Logger.getLogger(AdminController.class);
	@Autowired
	private IAdminService adminService;
	
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(
			HttpServletRequest request,
			@RequestParam(value = "loginId", defaultValue = "") String loginId,
			@RequestParam(value = "p", defaultValue = "1") int pageNO) {
		try {
			final int pageSize = 10;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("loginId", loginId);
			List<HashMap<String, Object>> list = adminService.query(pageNO, pageSize, params);
			long totalCount = adminService.totalCount(params);
			
			ModelAndView mv = new ModelAndView("admin/list");
			mv.addObject("pageNo", pageNO);
			mv.addObject("totalCount", totalCount);
			mv.addObject("pageSize", pageSize);
			mv.addObject("domain", this.getDomain(request));
			mv.addObject("link", "admin/list.html");
			mv.addObject("params", "loginId="+loginId);
			
			mv.addObject("list", list);
			mv.addObject("loginId", loginId);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
	/**
	 * 添加页面
	 * 
	 * @return
	 */
	@RequestMapping("addPage")
	public String addPage() {
		try {
			return "admin/add";
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
	/**
	 * 添加
	 */
	@RequestMapping(value="/add")
	@ResponseBody
	public Object add(
			HttpServletRequest request, 
			String loginId, 
			String password){
		try {
			if(StringUtils.isEmpty(loginId))
				return this.fail("账号不能为空!");
			if(StringUtils.isEmpty(password))
				return this.fail("密码不能为空!");
			//user
			Admin entity = this.adminService.findByLoginId(loginId);
			if(entity != null)
				return this.fail("账号已存在!");
			entity = new Admin();
			entity.setLoginId(loginId);
			entity.setPassword(MD5Util.encrypt(password));
			entity.setStatus(0);
			this.adminService.insert(entity);
			
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("系统错误!");
	}
	/**
	 * 修改页面
	 * 
	 * @return
	 */
	@RequestMapping("updatePage")
	public ModelAndView updatePage(int dbid) {
		try {
			Admin admin = this.adminService.findByDbid(dbid);
			
			ModelAndView mv = new ModelAndView("admin/update");
			mv.addObject("entity", admin);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
	/**
	 * 修改
	 * 
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Object update(
			int dbid, 
			String password,
			int status
			) {
		try {
			Admin entity = this.adminService.findByDbid(dbid);
			password = password.trim();
			if(!password.equals(entity.getPassword()))
			    entity.setPassword(MD5Util.encrypt(password));
			entity.setStatus(status);
			this.adminService.update(entity);
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("修改失败!");
	}
}
