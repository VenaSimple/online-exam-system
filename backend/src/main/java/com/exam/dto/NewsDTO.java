package com.exam.dto;

import lombok.Data;

@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private String summary;
    private String cover;
    private String author;
    private Integer viewCount;
    private Integer status;
}
