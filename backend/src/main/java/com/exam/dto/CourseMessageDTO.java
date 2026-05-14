package com.exam.dto;

import lombok.Data;

@Data
public class CourseMessageDTO {
    private Long id;
    private Long courseId;
    private Long userId;
    private String userName;
    private String userAvatar;
    private String content;
    private Long parentId;
    private String replyUserName;
    private Integer status;
}
