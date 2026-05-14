package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.UserDTO;
import com.exam.entity.User;

public interface UserService extends IService<User> {
    PageResult<UserDTO> listUsers(Integer pageNum, Integer pageSize, String keyword, String role);
    UserDTO getUserById(Long id);
    void createUser(UserDTO dto);
    void updateUser(UserDTO dto);
    void deleteUser(Long id);
    void changePassword(Long userId, String oldPassword, String newPassword);
    void resetPassword(Long userId);
    void updateStatus(Long id, Integer status);
}
