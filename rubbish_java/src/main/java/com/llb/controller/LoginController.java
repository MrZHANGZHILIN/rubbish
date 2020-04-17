package com.llb.controller;

import com.alibaba.fastjson.JSONObject;
import com.llb.common.CaptchaUtil;
import com.llb.common.MD5Util;
import com.llb.common.SessionUtil;
import com.llb.service.IAdminService;
import com.llb.entity.Admin;
import com.llb.service.IUserService;
import com.llb.service.impl.AdminService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller("loginController")
@RequestMapping("")
public class LoginController extends BaseController{
	private static final Logger L = Logger.getLogger(LoginController.class);
	
	@Autowired
	private IAdminService adminService;
	@Autowired
	private IUserService userService;
	
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login() {
		try {
			ModelAndView mv = new ModelAndView("login/login");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
	/**
	 * 登录
	 */
	@RequestMapping(value="/doLogin")
	@ResponseBody
	public Object do_login(
			HttpServletRequest request, 
			HttpServletResponse response,
			String loginId, 
			String pwd, 
			String captcha){
		try {
			//参数格式错误
			if(StringUtils.isBlank(loginId) || StringUtils.isBlank(pwd) || StringUtils.isBlank(captcha)){
				return this.fail("参数格式错误");
			}
			JSONObject json = (JSONObject)request.getSession().getAttribute("img_captcha");
			if(!json.getString("code").equals(captcha)){
				return this.fail("验证码错误");
			}
			if((System.currentTimeMillis() - json.getLong("createTime")) > 1000 * 60 * 5){
				return this.fail("验证码过期");
			}
			Admin admin = this.adminService.findByLoginId(loginId);

			if(admin == null){
				return this.fail("账号不存在");
			}
			if(!MD5Util.validPassword(pwd, admin.getPassword())){
				return this.fail("密码错误");
			}
			if(admin.getStatus() == 1){
				return this.fail("账号已禁用");
			}
			request.getSession().setAttribute(SessionUtil.user, admin);
			return this.success("登录成功");
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("系统错误");
	}
	/**
	 * 退出
	 */
	@RequestMapping("/loginout")
	@ResponseBody
	public Object loginout(HttpServletRequest request) {
		try {
			request.getSession().setAttribute(SessionUtil.user, null);
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return super.fail("退出失败");
	}
	/**
	 * 生产图形验证码
	 */
	@RequestMapping("/getCaptcha")
	public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
		try {
			String code = CaptchaUtil.createNewCaptcha();
			JSONObject json = new JSONObject();
			json.put("code", code);
			json.put("createTime", System.currentTimeMillis());
			// 将认证码存入SESSION
			request.getSession().setAttribute("img_captcha", json);
			//输出
			final Random random = new Random();
			final int codeCount = code.length();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			final int imgWidth = 100, imgHeight = 30;
			BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setColor(new Color(220, 220, 220));
			g.fillRect(0, 0, imgWidth, imgHeight);
			g.setFont(new Font("黑", Font.ITALIC | Font.BOLD, 12));
			for (int i = 0; i < 50; i++) {
				g.setColor(getRandColor());
				int x = random.nextInt(imgWidth);
				int y = random.nextInt(imgHeight);
				g.drawLine(x, y, x, y);
			}
			g.setFont(new Font("黑", Font.ITALIC | Font.BOLD, 24));
			g.setColor(new Color(0,0,0));
			for (int i = 0; i < codeCount; i++) {
				g.drawString(code.substring(i, i+1), 15 * i, 26);
			}
			g.dispose();
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Color getRandColor() {
		Random random = new Random();
		int r = random.nextInt(255);
		int g = random.nextInt(255);
		int b = random.nextInt(255);
		return new Color(r, g, b);
	}

	/**
	 * 微信小程序：用户授权登录接口
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/api/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getWxAuth(@RequestBody Map<String, String > map) {
		Map<String, Object> result = new HashMap<>();
		String code = map.get("code");
		if(code == null || "".equals(code)) {
			result.put("msg", "请重新授权");
		} else {
			result = userService.wxLogin(code);
		}
		return result;
	}
}
