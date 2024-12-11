package com.feedback.entity.query;

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
public class QuestionnaireQuery{


    /**
     * 问卷人姓名
     */
    private String name;

    /**
     * 专业技能
     */
    private String skill;

}
