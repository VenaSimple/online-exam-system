package com.exam.dto;

import lombok.Data;

@Data
public class KnowledgeDTO {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private Long categoryId;
    private String categoryName;
    private String cover;
    private Long authorId;
    private String authorName;
    private Integer viewCount;
    private Integer status;
    private String tags;
}
