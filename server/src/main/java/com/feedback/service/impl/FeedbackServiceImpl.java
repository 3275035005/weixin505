package com.feedback.service.impl;

import com.feedback.entity.Feedback;
import com.feedback.entity.query.FeedbackQuery;
import com.feedback.mapper.FeedbackMapper;
import com.feedback.service.FeedbackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.feedback.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 问题反馈 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

    @Override
    public PageResult pageQuery(FeedbackQuery feedbackQuery, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<Feedback> queryList = baseMapper.getQueryList(feedbackQuery);
        PageInfo<Feedback> pageInfo = new PageInfo<>(queryList);
        return new PageResult<>(pageInfo.getTotal(), queryList);
    }

    @Override
    public Feedback getFeedbackById(String id) {
        return baseMapper.getFeedbackById(id);
    }
}
