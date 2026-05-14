package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 试卷题目关联
 */
@Data
@TableName("exam_paper_question")
public class ExamPaperQuestion {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long paperId;
    private Long questionId;
    private Integer sort;
    private Integer score;      // 该题分值
}
