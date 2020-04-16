package com.llb.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

/**
 * 通用管理
 * @author llb
 *
 */
@Controller("commonController")
@RequestMapping("/common")
public class CommonController {
	private static final Logger L = Logger.getLogger(CommonController.class);
	
	/**
	 * 上传图片
	 */
	@RequestMapping("/uploadImg")
	@ResponseBody
	public Object uploadImg(
			HttpServletRequest request,
			@RequestParam("file") CommonsMultipartFile file) {
		try {
			String basePath = request.getSession().getServletContext().getRealPath("/");
			String relativePath = "uploadfile/images";
			File baseDir = new File(basePath + "/" + relativePath);
			if (!baseDir.exists()) {
				baseDir.mkdirs();
			}
			int index = file.getOriginalFilename().lastIndexOf(".");
			String ext = file.getOriginalFilename().substring(index);
					
			String newfilename = UUID.randomUUID().toString() + ext;
			File new_file = new File(baseDir, newfilename);
			file.transferTo(new_file);
			String url = relativePath + "/"+newfilename;
			JSONObject json = new JSONObject();
			json.put("errno", 0);
			json.put("data", new String[]{url});
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			L.error("------------------------", e);
		}
		return null;
	}
}
