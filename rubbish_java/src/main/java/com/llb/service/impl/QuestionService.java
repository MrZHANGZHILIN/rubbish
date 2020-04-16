package com.llb.service.impl;

import com.llb.common.PageUtil;
import com.llb.mapper.QuestionMapper;
import com.llb.entity.Question;
import com.llb.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionService implements IQuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	/**
	 * 添加
	 * @param entity
	 * @return
	 */
	public int insert(Question entity){
		return this.questionMapper.insert(entity);
	}
	/**
	 * 修改
	 * @param entity
	 * @return
	 */
	public int update(Question entity){
		return this.questionMapper.update(entity);
	}
	public Question findByDbid(int dbid){
		return questionMapper.findByDbid(dbid);
	}
	public Question findByIndex(int index){
		return questionMapper.findByIndex(index);
	}
	/**
	 * 通用查询
	 */
	public List<Question> find(Map<String, Object> map){
		return this.questionMapper.find(map);
	}
	/**
	 * 查询
	 */
	public List<HashMap<String, Object>> query(int pageNum, int pageSize, Map<String, Object> map){
		if (pageSize > 0) {
			map.put("pu", new PageUtil<>(pageNum, pageSize));
		}
		return this.questionMapper.query(map);
	}

	/**
	 * 获取记录数
	 */
	public long totalCount(Map<String, Object> map){
		return this.questionMapper.totalCount(map);
	}
	/**
	 * 删除
	 */
	public int delete(Map<String, Object> map){
		return this.questionMapper.delete(map);
	}
	/**
	 * 统计数量
	 */
	public long count(Map<String, Object> map){
		return this.questionMapper.count(map);
	}
}
