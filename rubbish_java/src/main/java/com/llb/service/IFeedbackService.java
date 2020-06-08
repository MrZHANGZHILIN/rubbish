package com.llb.service;

import com.llb.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author llb
 * @since 2020-05-26
 */
public interface IFeedbackService extends IService<Feedback> {

    /**
     * 保存反馈信息
     * @param feedback
     * @return
     */
    int insert(Feedback feedback);
}
