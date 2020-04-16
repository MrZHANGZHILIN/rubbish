package com.llb.common.interceptor;

import com.llb.common.SessionUtil;
import com.llb.entity.Admin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

	private List<String> excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
//		HandlerMethod handlerMethod = (HandlerMethod) handler;
//		Method method = handlerMethod.getMethod();
		String servletPath = req.getServletPath();
		for (String url : excludedUrls) { // 放行的请求
			if (servletPath.startsWith(url)) {
				return true;
			}
		}
		Admin admin = (Admin) req.getSession().getAttribute(SessionUtil.user);
		if (admin == null) {//未登录或会话超时
			resp.sendRedirect(getDomain(req) + "login.html");
			return false;
		}
		return true;
	}

	/**
	 * 获得域名
	 * 
	 * @param request
	 * @return
	 */
	protected String getDomain(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
		return basePath;
	}

}
