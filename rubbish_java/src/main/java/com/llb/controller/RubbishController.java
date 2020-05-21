package com.llb.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.llb.common.ResultInfo;
import com.llb.service.IBaiduAi;
import com.llb.service.IRubbishService;
import com.llb.entity.Rubbish;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	@ResponseBody
	public ModelAndView list(
			HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(defaultValue = "1", required = false, value = "page") Integer page,
			@RequestParam(defaultValue = "10", required = false, value = "limit") Integer limit) {
		try {
//			final int pageSize = 10;
			Map<String, Object> result = new HashMap<String, Object>();
			ModelAndView mv = new ModelAndView("rubbish/list");
			//分页操作
			Page<Map<String, Object>> pageParam = new Page<Map<String, Object>>(page, limit);
			IPage<Map<String, Object>> rubbishs = rubbishService.query(pageParam, name);
			if(rubbishs.getTotal() == 0) {
				result.put("code", 200);
				result.put("msg", "没有垃圾！");
			}

			mv.addObject("count", rubbishs.getTotal());
			mv.addObject("name", name);
			mv.addObject("list", rubbishs.getRecords());

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
		IPage<Map<String, Object>> rubbishs = rubbishService.query(pageParam, name);
		result.put("count", rubbishs.getTotal());
		result.put("list", rubbishs.getRecords());
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
			//调用百度接口
			ResultInfo<Object> resultInfo = baiduAi.garbageTextSearch(name);
			if(resultInfo.getCode() == 0) {
				List<Rubbish> rubbishes = (List<Rubbish>) resultInfo.getData();
				resultInfo.setData(rubbishes.get(0));
				return resultInfo;
			}
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
