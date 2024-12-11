package com.feedback.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feedback.entity.Questionnaire;
import com.feedback.entity.User;
import com.feedback.entity.query.QuestionnaireQuery;
import com.feedback.service.QuestionnaireService;
import com.feedback.service.UserService;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 问卷调查 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/questionnaire")
public class QuestionnaireController {

    @Autowired
    private QuestionnaireService questionnaireService;

    @Autowired
    private UserService userService;

    /**
     * 分页条件查询
     *
     * @param page           当前页码
     * @param limit          每页的大小
     * @param questionnaireQuery
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public ResultVo pageQuery(@PathVariable long page,
                              @PathVariable long limit,
                              @RequestBody QuestionnaireQuery questionnaireQuery) {
        QueryWrapper<Questionnaire> qw = new QueryWrapper<>();
        LambdaQueryWrapper<Questionnaire> lambda = qw.lambda();


        // 按照用户姓名查询
        if (!StringUtils.isEmpty(questionnaireQuery.getName())) {
            lambda.like(Questionnaire::getName, questionnaireQuery.getName());
        }

        if (!StringUtils.isEmpty(questionnaireQuery.getSkill())) {
            lambda.eq(Questionnaire::getUserId, questionnaireQuery.getSkill());
        }

        // 按照注册时间排序
        lambda.orderByDesc(Questionnaire::getCreated);
        Page<Questionnaire> pageParam = new Page<>(page, limit);

        IPage<Questionnaire> data = questionnaireService.page(pageParam, qw);
        List<Questionnaire> records = data.getRecords();
        for (Questionnaire record : records) {
            User user = userService.getById(record.getUserId());
            if(user != null){
                record.setAccount(user.getAccount());
            }
        }
        return ResultVo.success(data);
    }


    /**
     * 删除接口
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public ResultVo deleteById(@PathVariable String id) {
        questionnaireService.removeById(id);
        return ResultVo.success();
    }
}

