package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学习证明/证书
 */
@Data
@TableName("certificate")
public class Certificate {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String userName;
    private Long courseId;
    private String courseTitle;
    private String certificateNo;     // 证书编号
    private String template;           // 证书模板
    private Integer type;              // 1-结业证书 2-培训证明 3-成绩证明
    private LocalDateTime issueDate;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
