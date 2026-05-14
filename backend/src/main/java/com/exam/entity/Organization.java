package com.exam.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 机构/组织
 */
@Data
@TableName("organization")
public class Organization {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private Integer type;       // 1-公司 2-部门 3-小组
    private String leader;
    private String phone;
    private Integer sort;
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
