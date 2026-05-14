package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 学习记录
 */
@Data
@TableName("study_record")
public class StudyRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private Long courseId;
    private String courseTitle;
    private Long chapterId;
    private String chapterTitle;
    private Integer progress;       // 学习进度百分比
    private Integer duration;      // 学习时长(秒)
    private Integer lastPosition;  // 上次播放位置(秒)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
