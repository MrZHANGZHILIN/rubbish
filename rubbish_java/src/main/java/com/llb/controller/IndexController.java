package com.llb.controller;

import com.llb.common.SessionUtil;
import com.llb.entity.Admin;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController extends BaseController{
	private static final Logger L = Logger.getLogger(IndexController.class);

	/**
	 * 进入主页
	 * @return
	 */
	@RequestMapping("/")
	public ModelAndView rubbishIndex(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		Admin admin = (Admin) request.getSession().getAttribute("admin");
		if(admin == null) {
			mv.setViewName("/login/login");
		} else {
			mv.setViewName("/index/index");
		}
		return mv;
	}
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(
			HttpServletRequest request) {
		try {
			Admin admin = (Admin)request.getSession().getAttribute(SessionUtil.user);
			ModelAndView mv = new ModelAndView("index/index");
			mv.addObject("admin", admin);
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
	/**
	 * 欢迎页
	 * 
	 * @return
	 */
	@RequestMapping("/welcome")
	public String welcome() {
		return "index/welcome";
	}
}
