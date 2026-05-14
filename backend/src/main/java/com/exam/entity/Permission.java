package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private String resource;
    private Long parentId;
    private Integer type;
    private Integer sort;
}
