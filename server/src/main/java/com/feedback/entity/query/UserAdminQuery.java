package com.feedback.entity.query;

import lombok.Data;

@Data
public class UserAdminQuery {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 用户状态
     */
    private String status;
}
