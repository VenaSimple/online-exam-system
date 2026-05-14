package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 答题详情
 */
@Data
@TableName("exam_answer")
public class ExamAnswer {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long recordId;
    private Long questionId;
    private Integer questionType;
    private String questionContent;
    private String options;            // 题目选项
    private String correctAnswer;     // 正确答案
    private String userAnswer;        // 用户答案
    private Integer isCorrect;        // 是否正确 0-错误 1-正确 2-部分正确
    private Integer score;            // 得分
    private Integer questionScore;    // 题目分值
    private String analysis;          // 解析
}
