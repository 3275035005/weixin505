package com.feedback.service.impl;

import com.feedback.entity.Topic;
import com.feedback.mapper.TopicMapper;
import com.feedback.service.TopicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问题集 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
@Service
public class TopicServiceImpl extends ServiceImpl<TopicMapper, Topic> implements TopicService {

}
