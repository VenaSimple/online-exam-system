package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 题目分类
 */
@Data
@TableName("question_category")
public class QuestionCategory {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer questionCount;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
