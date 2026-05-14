package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("student_course")
public class StudentCourse {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long courseId;
    private String courseTitle;
    private Integer progress;
    private Integer status;
    private Long assignBy;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
