package com.llb.controller;

import com.alibaba.fastjson.JSONArray;
import com.llb.common.ResultInfo;
import com.llb.service.IBaiduAi;
import com.llb.service.ICategoryService;
import com.llb.entity.Category;
import com.llb.service.impl.CategoryService;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 垃圾分类管理
 * @author llb
 *
 */
@Controller("categoryController")
@RequestMapping("/category")
@Log4j
public class CategoryController extends BaseController{
	private static final Logger L = Logger.getLogger(CategoryController.class);
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private IBaiduAi baiduAi;
	@Value("${upload_path}")
	private String upload_path;


	/**upload_path
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
			List<HashMap<String, Object>> list = categoryService.query(pageNO, pageSize, params);
			long totalCount = categoryService.totalCount(params);
			
			
			ModelAndView mv = new ModelAndView("category/list");
			mv.addObject("pageNo", pageNO);
			mv.addObject("totalCount", totalCount);
			mv.addObject("pageSize", pageSize);
			mv.addObject("domain", this.getDomain(request));
			mv.addObject("link", "category/list.html");
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
			return "category/add";
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
			String img,
			String des,
			String guide,
			int order){
		try {
			Category entity = new Category();
			entity.setName(name);
			entity.setImg(img);
			entity.setDes(des);
			entity.setGuide(guide);
			entity.setOrder(order);
			this.categoryService.insert(entity);
			
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
			Category entity = this.categoryService.findByDbid(dbid);
			
			ModelAndView mv = new ModelAndView("category/update");
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
			String img,
			String des,
			String guide,
			int order
			) {
		try {
			Category entity = this.categoryService.findByDbid(dbid);
			entity.setName(name);
			entity.setImg(img);
			entity.setDes(des);
			entity.setGuide(guide);
			entity.setOrder(order);
			this.categoryService.update(entity);
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
			this.categoryService.delete(params);
			
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
	@RequestMapping(value="/api/list")
	@ResponseBody
	public ResultInfo<Object> list(
			HttpServletRequest request){
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", "");
			List<Category> list = this.categoryService.find(params);
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
			Category entity = this.categoryService.findByDbid(dbid);
			return new ResultInfo<Object>(0, entity);
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}
		return new ResultInfo<Object>(1000, "未知错误"); 
	}

	/**
	 * 图片识别API
	 * @return
	 */
	@RequestMapping("/api/uploadImage")
	@ResponseBody
	public Object uploadImage(HttpServletRequest request) {
		MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
		MultipartFile multipartFile =  req.getFile("file");
		String realPath = upload_path;//服务器存放图片地址
		try {
			File dir = new File(realPath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			String newPath = System.currentTimeMillis()+""+(int)(1+Math.random()*(10000-1+1))+".jpg";//图片名称是毫秒数加1-10000的随机数
			File file  =  new File(realPath,newPath);
			multipartFile.transferTo(file);
			//图片识别
			ResultInfo<Object> resultInfo = baiduAi.imageRecognition(file);
			L.info("图片识别成功：" + resultInfo);
			return resultInfo.getData();
		} catch (Exception e) {
			e.printStackTrace();
			L.error("-------------", e);
		}

		return new ResultInfo<>(1000, "未知错误");
	}

	/**
	 * 查询全部
	 */
	@RequestMapping("/findAll")
	@ResponseBody
	public JSONArray findAll(HttpServletRequest request) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			List<Category> list = categoryService.find(params);
			JSONArray arr = new JSONArray();
			for (Category item : list) {
				arr.add(new String[] { String.valueOf(item.getDbid()), item.getName() });
			}
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
}
