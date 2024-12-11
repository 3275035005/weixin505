package com.feedback.mapper;

import com.feedback.entity.Feedback;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.feedback.entity.query.FeedbackQuery;

import java.util.List;

/**
 * <p>
 * 问题反馈 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

    List<Feedback> getQueryList(FeedbackQuery feedbackQuery);

    Feedback getFeedbackById(String id);
}
