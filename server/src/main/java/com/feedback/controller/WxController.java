package com.feedback.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.feedback.entity.Feedback;
import com.feedback.entity.Questionnaire;
import com.feedback.entity.Topic;
import com.feedback.entity.User;
import com.feedback.service.FeedbackService;
import com.feedback.service.QuestionnaireService;
import com.feedback.service.TopicService;
import com.feedback.service.UserService;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对小程序端 提供接口
 */
@RestController
@RequestMapping("wx")
public class WxController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private UserService userService;

    /**
     * 登录功能
     * @return
     */
    @PostMapping("login")
    public ResultVo login(@RequestBody User user) {

        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("account", user.getAccount());
        qw.eq("password", user.getPassword());
        qw.eq("type", "0");
        User one = userService.getOne(qw);
        if (one == null) {
            return ResultVo.fail("账号或密码不正确");
        }
        if ("1".equals(one.getStatus())) {
            return ResultVo.fail("账号已被禁用");
        }
        return ResultVo.success(one.getId());
    }

    /**
     * 注册功能
     * @return
     */
    @PostMapping("register")
    public ResultVo register(@RequestBody User user) {
        user.setType("0");
        user.setStatus("0");
        userService.save(user);
        return ResultVo.success();
    }


    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @GetMapping("getUserInfo/{id}")
    public ResultVo getUserInfo(@PathVariable String id) {
        User user = userService.getById(id);
        return ResultVo.success(user);
    }

    /**
     * 用户信息修改
     */
    @PostMapping("updateInfo")
    public ResultVo getUserInfo(@RequestBody User user) {
        userService.updateById(user);
        return ResultVo.success();
    }

    /**
     * 问卷调查的提交功能
     * @param questionnaire
     * @return
     */
    @PostMapping("sendQuestionnaire")
    public ResultVo sendQuestionnaire(@RequestBody Questionnaire questionnaire){
        Questionnaire questionnaire1 = questionnaireService.getOne(new QueryWrapper<Questionnaire>().eq("user_id", questionnaire.getUserId()));
        if(questionnaire1 != null){
            return ResultVo.fail("抱歉,您已经提交过一次了");
        }
        questionnaireService.save(questionnaire);
        return ResultVo.success();
    }


    /**
     * 问题反馈的提交功能
     * @param feedback
     * @return
     */
    @PostMapping("sendFeedback")
    public ResultVo sendFeedback(@RequestBody Feedback feedback){
        Topic topic = topicService.getOne(new QueryWrapper<Topic>().eq("title", feedback.getTitle()));
        feedback.setStatus("0");
        feedback.setTopicId(topic.getId());
        feedbackService.save(feedback);
        return ResultVo.success();
    }

    /**
     * 查询所有问卷集
     * @return
     */
    @GetMapping("getTopicAll")
    public ResultVo getTopicAll(){

        List<Topic> list = topicService.list(null);
        List<String> lists = new ArrayList<>();
        lists.add("请选择反馈类别");
        for (Topic topic : list) {
            lists.add(topic.getTitle());
        }
        return ResultVo.success(lists);
    }


    /**
     * 反馈列表查询
     * @return
     */
    @PostMapping("feedbackList")
    public ResultVo feedbackList(@RequestBody Feedback feedback){
        QueryWrapper<Feedback> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Feedback> lambda = qw.lambda();

        if(!"请选择反馈类别".equals(feedback.getTitle()) && !StringUtils.isEmpty(feedback.getTitle())){
            Topic topic = topicService.getOne(new QueryWrapper<Topic>().eq("title", feedback.getTitle()));
            lambda.eq(Feedback::getTopicId, topic.getId());
        }
        lambda.eq(Feedback::getStatus, feedback.getStatus());
        lambda.eq(Feedback::getUserId, feedback.getUserId());
        lambda.orderByDesc(Feedback::getCreated);
        List<Feedback> list = feedbackService.list(qw);
        return ResultVo.success(list);
    }

    /**
     * 通过id获取问题反馈详情数据
     * @param id
     * @return
     */
    @GetMapping("getFeedbackById/{id}")
    public ResultVo getFeedbackById(@PathVariable String id){
        Feedback feedback = feedbackService.getFeedbackById(id);
        return ResultVo.success(feedback);
    }

}
