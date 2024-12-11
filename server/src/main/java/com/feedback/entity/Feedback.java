package com.feedback.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 问题反馈
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 问题集id
     */
    private String topicId;

    /**
     * 反馈内容
     */
    private String content;

    /**
     *反馈人姓名
     */
    private String name;
    /**
     * 反馈user.id
     */
    private String userId;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 反馈图片
     */
    private String image;

    /**
     * 反馈时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String created;

    /**
     * 回复内容
     */
    private String acontent;

    /**
     * 回复状态
     */
    private String status;

    /**
     * 回复时间
     */
    private String acreated;

    /**
     * 回复人userid
     */
    private String auserId;

    /**
     * 反馈学生登录账号
     */
    @TableField(exist = false)
    private String account;

    /**
     * 回复人姓名
     */
    @TableField(exist = false)
    private String aname;

    /**
     * 问题集
     */
    @TableField(exist = false)
    private String title;
}
