package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 课程评价
 */
@Data
@TableName("course_review")
public class CourseReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private Long userId;
    private String userName;
    private Integer score;       // 评分1-5
    private String content;      // 评价内容
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
