package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体
 */
@Data
@TableName("course")
public class Course {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String cover;
    private String description;
    private Long categoryId;
    private Long teacherId;
    private String teacherName;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer status;          // 0-草稿 1-已发布 2-下架
    private Integer isFree;           // 0-收费 1-免费
    private Integer studentCount;     // 学习人数
    private Integer viewCount;        // 浏览量
    private BigDecimal avgScore;      // 平均评分
    private String tags;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
