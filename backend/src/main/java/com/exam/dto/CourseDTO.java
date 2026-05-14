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
    private Long teacherId;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer status;
    private Integer isFree;
    private String tags;
}
