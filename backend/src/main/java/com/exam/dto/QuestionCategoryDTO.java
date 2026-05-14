package com.exam.dto;

import lombok.Data;

@Data
public class QuestionCategoryDTO {
    private Long id;
    private String name;
    private Long parentId;
    private Integer sort;
    private Integer questionCount;
}
