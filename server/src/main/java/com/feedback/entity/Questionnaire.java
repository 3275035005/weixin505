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
 * 问卷调查
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 问卷人姓名
     */
    private String name;

    /**
     * 问卷人联系电话
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 专业技能
     */
    private String skill;

    /**
     * 意见
     */
    private String opinion;

    /**
     * 问卷人userid
     */
    private String userId;

    /**
     * 提交问卷时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String created;

    /**
     * 登录账号
     */
    @TableField(exist = false)
    private String account;

}
