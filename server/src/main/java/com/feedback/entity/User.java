package com.feedback.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * sh_user用户实体类
 * @author myl
 */
@TableName("user")
@Data
public class User implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 账号
     */
    private String account;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;


    /**
     * 登录角色 0学生 1老师
     */
    private String type;

    /**
     * 班级
     */
    private String banji;

    /**
     * 专业
     */
    private String zhuanye;

    /**
     * 用户状态(0，正常 1,禁用)
     */
    private String status;

    /**
     * 注册时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String created;

    @TableField(exist = false)
    private String[] roles;
}
