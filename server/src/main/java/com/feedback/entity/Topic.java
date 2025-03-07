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
 * 问题集
 * </p>
 *
 * @author 
 * @since 2023-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * 问题集名称
     */
    private String title;

    /**
     * 问题集创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String created;

    /**
     * 问题集数量
     */
    @TableField(exist = false)
    private Integer sum;

}
