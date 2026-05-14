package com.exam.dto;

import lombok.Data;

@Data
public class OrganizationDTO {
    private Long id;
    private String name;
    private String code;
    private Long parentId;
    private Integer type;
    private String leader;
    private String phone;
    private Integer sort;
    private Integer status;
}
