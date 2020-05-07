package com.llb.controller;

import com.llb.common.ResultInfo;
import com.llb.service.IBaiduAi;
import com.llb.service.IRubbishService;
import com.llb.entity.Rubbish;
import com.llb.service.impl.RubbishService;
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
 * 垃圾管理
 * @author llb
 *
 */
@Controller("rubbishController")
@RequestMapping("/rubbish")
public class RubbishController extends BaseController{
	private static final Logger L = Logger.getLogger(RubbishController.class);
	@Autowired
	private IRubbishService rubbishService;
	@Autowired
	private IBaiduAi baiduAi;
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
			List<HashMap<String, Object>> list = rubbishService.query(pageNO, pageSize, params);
			long totalCount = rubbishService.totalCount(params);
			
			
			ModelAndView mv = new ModelAndView("rubbish/list");
			mv.addObject("pageNo", pageNO);
			mv.addObject("totalCount", totalCount);
			mv.addObject("pageSize", pageSize);
			mv.addObject("domain", this.getDomain(request));
			mv.addObject("link", "rubbish/list.html");
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
			return "rubbish/add";
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
			String ps,
			int categoryId,
			String hot){
		try {
			Rubbish entity = new Rubbish();
			entity.setName(name);
			entity.setCategoryId(categoryId);
			entity.setPs(ps);
			entity.setHot(hot);
			this.rubbishService.insert(entity);
			
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
			Rubbish entity = this.rubbishService.findByDbid(dbid);
			
			ModelAndView mv = new ModelAndView("rubbish/update");
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
			int categoryId,
			String ps,
			String hot
			) {
		try {
			Rubbish entity = this.rubbishService.findByDbid(dbid);
			entity.setName(name);
			entity.setCategoryId(categoryId);
			entity.setPs(ps);
			entity.setHot(hot);
			this.rubbishService.update(entity);
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
			this.rubbishService.delete(params);
			
			return this.success(null);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return this.fail("删除失败!");
	}
	/**
	 * 查询(api)
	 */
	@RequestMapping(value="/api/hot")
	@ResponseBody
	public ResultInfo<Object> hot(
			HttpServletRequest request){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hot", "yes");
			List<Rubbish> list = this.rubbishService.find(params);
			return new ResultInfo<Object>(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}
	/**
	 * 查询(api)
	 */
	@RequestMapping(value="/api/get")
	@ResponseBody
	public ResultInfo<Object> get(
			HttpServletRequest request,
			int dbid){
		try {
			Rubbish entity = this.rubbishService.findByDbid(dbid);
			return new ResultInfo<Object>(0, entity);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}
	/**
	 * 查询(api)
	 */
	@RequestMapping(value="/api/getByName")
	@ResponseBody
	public ResultInfo<Object> getByName(
			HttpServletRequest request,
			String name){
		try {
			Rubbish entity = this.rubbishService.findByName(name);
			if(entity != null)
			  return new ResultInfo<Object>(0, entity);
			return new ResultInfo<Object>(10, "未找到垃圾");
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}
	/**
	 * 查询(api)
	 */
	@RequestMapping(value="/api/getByKeyword")
	@ResponseBody
	public ResultInfo<Object> getByKeyword(
			HttpServletRequest request,
			String keyword){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("keyword", keyword);
			List<Rubbish> list = this.rubbishService.find(params);
			if(list.size() == 0) {
				return baiduAi.garbageTextSearch(keyword);
			}
			//如果调用
			return new ResultInfo<Object>(0, list);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}
}
