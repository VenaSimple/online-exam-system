package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 课程章节
 */
@Data
@TableName("course_chapter")
public class CourseChapter {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long courseId;
    private String title;
    private Long parentId;
    private Integer sort;
    private Integer type;         // 1-章节 2-课时
    private Long mediaId;         // 关联媒体资源ID
    private String mediaUrl;      // 视频/文档地址
    private Integer duration;     // 时长(秒)
    private Integer isFree;       // 0-否 1-免费试看
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
