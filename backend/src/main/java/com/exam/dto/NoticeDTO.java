package com.exam.dto;

import lombok.Data;

@Data
public class NoticeDTO {
    private Long id;
    private String title;
    private String content;
    private Integer type;
    private Integer isTop;
    private Integer status;
    private Long publisherId;
    private String publisherName;
}
