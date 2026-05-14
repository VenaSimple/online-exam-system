package com.exam.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String realName;
    private String avatar;
    private String email;
    private String phone;
    private String role;
    private Integer status;
    private Long orgId;
}
