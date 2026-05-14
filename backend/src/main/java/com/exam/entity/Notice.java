package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 通知公告
 */
@Data
@TableName("notice")
public class Notice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Integer type;          // 1-通知 2-公告
    private Integer isTop;        // 是否置顶 0-否 1-是
    private Long courseId;         // 所属课程ID
    private Integer status;          // 0-草稿 1-已发布
    private Long publisherId;
    private String publisherName;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}
