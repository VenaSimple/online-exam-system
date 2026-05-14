package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 题目实体
 */
@Data
@TableName("question")
public class Question {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String content;        // 题目内容
    private Integer type;         // 1-单选 2-多选 3-判断 4-填空 5-简答
    private String options;        // 选项JSON: [{"key":"A","value":"选项A"},...]
    private String answer;         // 正确答案
    private String analysis;       // 解析
    private Long categoryId;      // 题目分类ID
    private String categoryName;  // 分类名称
    private Integer difficulty;    // 难度: 1-简单 2-中等 3-困难
    private Integer score;         // 分值
    private Long creatorId;        // 创建者ID
    private String creatorName;    // 创建者姓名
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
