package com.llb.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.entity.Feedback;
import com.llb.entity.User;
import com.llb.service.IFeedbackService;
import com.llb.service.IUserService;
import com.llb.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
	@Autowired
	private IFeedbackService feedbackService;
	
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(
			HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(defaultValue = "1", required = false, value = "page") Integer page,
			@RequestParam(defaultValue = "10", required = false, value = "limit") Integer limit) {
		try {
			final int pageSize = 10;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			ModelAndView mv = new ModelAndView("user/list");
			//分页操作
			Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, limit);
			IPage<Map<String, Object>> rubbishs = userService.query(pageParam, name);

			mv.addObject("list", rubbishs.getRecords());
			mv.addObject("count", rubbishs.getTotal());
			mv.addObject("name", name);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}

	/**
	 * 分页操作
	 * @param name
	 * @param page
	 * @param limit
	 * @return
	 */
	@RequestMapping("pageList")
	@ResponseBody
	public Map<String, Object> pageList(@RequestParam(value = "name", defaultValue = "") String name,
										@RequestParam(defaultValue = "1", required = true, value = "page") Integer page,
										@RequestParam(defaultValue = "10", required = true, value = "limit") Integer limit) {
		Map<String, Object> result = new HashMap<>();
		//分页操作
		Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, limit);
		IPage<Map<String, Object>> users = userService.query(pageParam, name);
		result.put("count", users.getTotal());
		result.put("list", users.getRecords());
		result.put("name", name);
		return result;
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

	/**
	 * 保存用户信息
	 * @return
	 */
	@RequestMapping("/api/saveUser")
	@ResponseBody
	public Map<String, Object> saveUser(@RequestBody JSONObject jsonObject) {
		Map<String, Object> result = new HashMap<>();
		User user = jsonObject.getObject("user", User.class);
		user.setOpenid(jsonObject.getString("openId"));
		User saveUser = userService.findByOpenid(jsonObject.getString("openId"));
		//判断用户是否登录
		if(saveUser == null) {
			user.setCtime(new DateUtil().formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
			userService.insert(user);
		} else {
			userService.update(user);
		}
		result.put("code", 200);
		result.put("msg", "保存成功");
		result.put("data", user);
		return result;
	}

	/**
	 * 用户反馈
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/api/feedBack")
	@ResponseBody
	public Map<String, Object> feedBack(@RequestBody Map<String, Object> map) {
		Map<String, Object> result = new HashMap<>();
		//补全反馈信息
		Feedback feedback = new Feedback();
		feedback.setFeedId(UUID.randomUUID().toString().replace("-", ""));
		feedback.setOpenId((String) map.get("openid"));
		feedback.setContent((String) map.get("detail"));
		feedback.setEmail((String) map.get("email"));
		feedback.setImgurl1((String) map.get("email"));
		if(!"".equals(map.get("imgList"))) {
			List<String> imgList = (List<String>) map.get("imgList");

			//TODO:好烂好烂好烂！！！
			if (imgList.size() == 4) {
				feedback.setImgurl1(imgList.get(0));
				feedback.setImgurl2(imgList.get(1));
				feedback.setImgurl3(imgList.get(2));
				feedback.setImgurl4(imgList.get(3));
			} else if(imgList.size() == 3) {
				feedback.setImgurl1(imgList.get(0));
				feedback.setImgurl2(imgList.get(1));
				feedback.setImgurl3(imgList.get(2));
			} else if(imgList.size() == 2){
				feedback.setImgurl1(imgList.get(0));
				feedback.setImgurl2(imgList.get(1));
			} else if(imgList.size() == 1){
				feedback.setImgurl1(imgList.get(0));
			}
		} else {
		}
		feedback.setCreateDate(new DateUtil().formatDate(new Date(), "yyyy-MM-dd hh:mm:ss"));
		//保存
		try {
			feedbackService.insert(feedback);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("msg", e);
			return result;
		}
		result.put("code", 200);
		result.put("msg", "反馈成功！");
		return result;
	}
}
