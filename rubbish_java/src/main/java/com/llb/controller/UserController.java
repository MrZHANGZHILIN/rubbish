package com.llb.controller;

import com.llb.common.MD5Util;
import com.llb.entity.Admin;
import com.llb.entity.User;
import com.llb.service.IAdminService;
import com.llb.service.IUserService;
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
 * 用户管理
 * @author llb
 *
 */
@Controller("userController")
@RequestMapping("/user")
public class UserController extends BaseController{
	private static final Logger L = Logger.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(
			HttpServletRequest request,
			@RequestParam(value = "openid", required = true) String openid,
			@RequestParam(value = "p", defaultValue = "1") int pageNO) {
		try {
			final int pageSize = 10;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("openid", openid);
			List<Map<String, Object>> list = userService.query(pageNO, pageSize, params);
			long totalCount = userService.totalCount(params);
			
			ModelAndView mv = new ModelAndView("user/list");
			mv.addObject("pageNo", pageNO);
			mv.addObject("totalCount", totalCount);
			mv.addObject("pageSize", pageSize);
			mv.addObject("domain", this.getDomain(request));
			mv.addObject("link", "user/list.html");
			mv.addObject("params", "openid="+openid);
			
			mv.addObject("list", list);
			mv.addObject("openid", openid);
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
			return "user/add";
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
			String openid,
			String password){
		try {
			if(StringUtils.isEmpty(openid))
				return this.fail("账号不能为空!");
			if(StringUtils.isEmpty(password))
				return this.fail("密码不能为空!");
			//user
			User entity = this.userService.findByOpenid(openid);
			if(entity != null)
				return this.fail("账号已存在!");
			entity = new User();
//			entity.setLoginId(loginId);
//			entity.setPassword(MD5Util.encrypt(password));
//			entity.setStatus(0);
			this.userService.insert(entity);
			
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
	/*@RequestMapping("updatePage")
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
	*//**
	 * 修改
	 * 
	 * @return
	 *//*
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
	}*/
}
