package com.feedback.service;

import com.feedback.entity.Feedback;
import com.baomidou.mybatisplus.extension.service.IService;
import com.feedback.entity.query.FeedbackQuery;
import com.feedback.utils.PageResult;

/**
 * <p>
 * 问题反馈 服务类
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
public interface FeedbackService extends IService<Feedback> {

    PageResult pageQuery(FeedbackQuery feedbackQuery, int page, int limit);

    Feedback getFeedbackById(String id);
}
