package com.exam.dto;

import lombok.Data;

@Data
public class CourseChapterDTO {
    private Long id;
    private Long courseId;
    private String title;
    private Long parentId;
    private Integer sort;
    private Integer type;
    private Long mediaId;
    private String mediaUrl;
    private Integer duration;
    private Integer isFree;
}
