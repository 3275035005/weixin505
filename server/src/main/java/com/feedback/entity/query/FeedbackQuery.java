package com.feedback.entity.query;

import lombok.Data;

/**
 * <p>
 * 问题反馈
 * </p>
 *
 */
@Data
public class FeedbackQuery {


    /**
     * 问题集id
     */
    private String topicId;

    /**
     * 反馈user.id
     */
    private String userId;

    /**
     * 回复状态
     */
    private String status;



}
