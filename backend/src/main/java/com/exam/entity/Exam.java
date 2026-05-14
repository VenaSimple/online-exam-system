package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 考试实体
 */
@Data
@TableName("exam")
public class Exam {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private Long paperId;
    private String paperTitle;
    private Integer examType;          // 1-正式考试 2-模拟考试 3-练习
    private Integer status;            // 0-草稿 1-已发布 2-进行中 3-已结束
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;          // 考试时长(分钟)
    private Integer totalScore;
    private Integer passScore;
    private Integer maxAttempt;        // 最大尝试次数
    private Integer showAnswer;       // 是否显示答案: 0-否 1-交卷后 2-结束后
    private Integer showAnalysis;     // 是否显示解析
    private String participantIds;     // 参与人员ID(逗号分隔)
    private String participantType;    // participant/dept/role/all
    private Long creatorId;
    private String creatorName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
