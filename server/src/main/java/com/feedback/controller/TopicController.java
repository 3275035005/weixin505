package com.feedback.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feedback.entity.Feedback;
import com.feedback.entity.Topic;
import com.feedback.entity.query.TopicQuery;
import com.feedback.service.FeedbackService;
import com.feedback.service.TopicService;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 问题集 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 分页条件查询
     *
     * @param page           当前页码
     * @param limit          每页的大小
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public ResultVo pageQuery(@PathVariable long page,
                              @PathVariable long limit,
                              @RequestBody TopicQuery topicQuery) {
        QueryWrapper<Topic> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Topic> lambda = qw.lambda();

        // 按照用户姓名查询
        if (!StringUtils.isEmpty(topicQuery.getTitle())) {
            lambda.like(Topic::getTitle, topicQuery.getTitle());
        }

        // 按照注册时间排序
        lambda.orderByDesc(Topic::getCreated);
        Page<Topic> pageParam = new Page<>(page, limit);

        IPage<Topic> data = topicService.page(pageParam, qw);
        List<Topic> records = data.getRecords();
        for (Topic record : records) {
            record.setSum(feedbackService.count(new QueryWrapper<Feedback>().eq("topic_id", record.getId())));
        }
        return ResultVo.success(data);
    }


    /**
     * 新增接口
     *
     * @param TopicModel
     * @return
     */
    @PostMapping("insert")
    public ResultVo insert(@RequestBody Topic TopicModel) {
        topicService.save(TopicModel);
        return ResultVo.success();
    }

    /**
     * 修改接口
     *
     * @param TopicModel
     * @return
     */
    @PutMapping("update")
    public ResultVo update(@RequestBody Topic TopicModel) {

        topicService.updateById(TopicModel);
        return ResultVo.success();
    }

    /**
     * 删除接口
     *
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public ResultVo deleteById(@PathVariable String id) {
        topicService.removeById(id);
        return ResultVo.success();
    }


    /**
     * 查询所有问题集
     *
     * @return
     */
    @GetMapping("getTopicAll")
    public ResultVo getUserAll() {
        List<Topic> list = topicService.list(null);
        return ResultVo.success(list);
    }

}

