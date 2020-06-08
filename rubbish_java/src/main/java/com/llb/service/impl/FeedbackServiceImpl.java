package com.llb.service.impl;

import com.llb.entity.Feedback;
import com.llb.mapper.FeedbackMapper;
import com.llb.service.IFeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author llb
 * @since 2020-05-26
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;
    /**
     * 保存反馈信息
     * @param feedback
     * @return
     */
    @Override
    public int insert(Feedback feedback) {
        feedbackMapper.insertFeedBack(feedback);
        return 0;
    }
}
