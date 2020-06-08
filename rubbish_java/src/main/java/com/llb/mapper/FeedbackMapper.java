package com.llb.mapper;

import com.llb.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author llb
 * @since 2020-05-26
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    int insertFeedBack(Feedback feedback);
}
