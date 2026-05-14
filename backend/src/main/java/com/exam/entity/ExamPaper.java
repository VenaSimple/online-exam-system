package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 试卷实体
 */
@Data
@TableName("exam_paper")
public class ExamPaper {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private Integer totalScore;        // 总分
    private Integer passScore;         // 及格分
    private Integer duration;          // 考试时长(分钟)
    private Integer questionCount;     // 题目数量
    private Integer type;              // 1-固定试卷 2-随机试卷
    private Long categoryId;           // 试卷分类
    private Long courseId;         // 所属课程ID
    private Long creatorId;
    private String creatorName;
    private Integer status;            // 0-草稿 1-启用
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
