package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("major")
public class Major {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private Long departmentId;
    private String description;
    private Integer sort;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
