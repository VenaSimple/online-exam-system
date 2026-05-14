package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("exam_session")
public class ExamSession {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long examId;
    private String sessionName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
