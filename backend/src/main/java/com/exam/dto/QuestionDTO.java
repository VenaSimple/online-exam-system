package com.exam.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private Long id;
    private String content;
    private Integer type;
    private String options;
    private String answer;
    private String analysis;
    private Long categoryId;
    private Long courseId;
    private String categoryName;
    private Integer difficulty;
    private Integer score;
    private Long creatorId;
    private String creatorName;
    private java.time.LocalDateTime createTime;
}
