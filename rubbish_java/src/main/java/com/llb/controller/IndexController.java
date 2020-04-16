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
