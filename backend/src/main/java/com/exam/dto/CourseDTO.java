package com.exam.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String cover;
    private String description;
    private Long categoryId;
    private String categoryName;
    private Long teacherId;
    private String teacherName;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer status;
    private Integer isFree;
    private String tags;
    private Integer studentCount;
    private Integer viewCount;
    private BigDecimal avgScore;
    private java.time.LocalDateTime createTime;
}
