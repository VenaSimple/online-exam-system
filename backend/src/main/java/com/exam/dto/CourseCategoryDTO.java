package com.exam.dto;

import lombok.Data;

@Data
public class CourseCategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private String icon;
    private Integer status;
}
