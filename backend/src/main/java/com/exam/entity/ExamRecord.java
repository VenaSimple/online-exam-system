package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 考试记录(答题记录)
 */
@Data
@TableName("exam_record")
public class ExamRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long examId;
    private String examTitle;
    private Long paperId;
    private Long userId;
    private String userName;
    private Integer userScore;         // 得分
    private Integer totalScore;        // 总分
    private Integer passScore;         // 及格分
    private Integer isPass;           // 是否及格 0-否 1-是
    private Integer status;            // 0-未开始 1-答题中 2-已交卷 3-已批阅
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;          // 用时(秒)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
