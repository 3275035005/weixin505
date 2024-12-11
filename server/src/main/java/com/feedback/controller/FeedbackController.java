package com.feedback.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.feedback.entity.Feedback;
import com.feedback.entity.User;
import com.feedback.entity.query.FeedbackQuery;
import com.feedback.service.FeedbackService;
import com.feedback.service.UserService;
import com.feedback.utils.PageResult;
import com.feedback.utils.Utils;
import com.feedback.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 问题反馈 前端控制器
 * </p>
 *
 * @author
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

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
    public ResultVo pageQuery(@PathVariable int page,
                              @PathVariable int limit,
                              @RequestBody FeedbackQuery FeedbackQuery) {
        PageResult pageResult = feedbackService.pageQuery(FeedbackQuery, page, limit);
        return ResultVo.success(pageResult);
    }

    
    /**
     * 回复
     *
     * @param FeedbackModel
     * @return
     */
    @PutMapping("update")
    public ResultVo update(@RequestBody Feedback FeedbackModel) {
        FeedbackModel.setStatus("1");
        FeedbackModel.setAcreated(Utils.getCurrentTime());
        feedbackService.updateById(FeedbackModel);
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
        feedbackService.removeById(id);
        return ResultVo.success();
    }
}

