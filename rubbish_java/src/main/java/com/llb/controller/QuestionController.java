package com.llb.controller;

import com.llb.common.ResultInfo;
import com.llb.service.IQuestionService;
import com.llb.entity.Question;
import com.llb.service.impl.QuestionService;
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
 * 试题管理
 * @author llb
 *
 */
@Controller("questionController")
@RequestMapping("/question")
public class QuestionController extends BaseController{
	private static final Logger L = Logger.getLogger(QuestionController.class);
	@Autowired
	private IQuestionService questionService;
	
	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping("list")
	public ModelAndView list(
			HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "p", defaultValue = "1") int pageNO) {
		try {
			final int pageSize = 10;
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", name);
			List<HashMap<String, Object>> list = questionService.query(pageNO, pageSize, params);
			long totalCount = questionService.totalCount(params);
			
			
			ModelAndView mv = new ModelAndView("question/list");
			mv.addObject("pageNo", pageNO);
			mv.addObject("totalCount", totalCount);
			mv.addObject("pageSize", pageSize);
			mv.addObject("domain", this.getDomain(request));
			mv.addObject("link", "question/list.html");
			mv.addObject("params", "name="+name);
			
			mv.addObject("list", list);
			mv.addObject("name", name);
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
			return "question/add";
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
			String name, 
			String a,
			String b,
			String c,
			String d,
			String answer){
		try {
			Question entity = new Question();
			entity.setName(name);
			entity.setA(a);
			entity.setB(b);
			entity.setC(c);
			entity.setD(d);
			entity.setAnswer(answer);
			this.questionService.insert(entity);
			
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
			Question entity = this.questionService.findByDbid(dbid);
			
			ModelAndView mv = new ModelAndView("question/update");
			mv.addObject("entity", entity);
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
			String name, 
			String a,
			String b,
			String c,
			String d,
			String answer
			) {
		try {
			Question entity = this.questionService.findByDbid(dbid);
			entity.setName(name);
			entity.setA(a);
			entity.setB(b);
			entity.setC(c);
			entity.setD(d);
			entity.setAnswer(answer);
			this.questionService.update(entity);
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("修改失败!");
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(
			HttpServletRequest request,
			String dbids
			) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dbids", dbids.split(","));
			this.questionService.delete(params);
			
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("删除失败!");
	}
	/**
	 * 统计试题总数(api)
	 */
	@RequestMapping(value="/api/count")
	@ResponseBody
	public ResultInfo<Object> count(){
		try {
			long count = this.questionService.count(new HashMap<String, Object>());
			return new ResultInfo<Object>(0, count);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}
	/**
	 * 根据下标获得(api)
	 */
	@RequestMapping(value="/api/findByIndex")
	@ResponseBody
	public ResultInfo<Object> findByIndex(int index){
		try {
			Question entity = this.questionService.findByIndex(index);
			return new ResultInfo<Object>(0, entity);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}

}
