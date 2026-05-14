package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.UserDTO;
import com.exam.entity.User;
import com.exam.mapper.UserMapper;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public PageResult<UserDTO> listUsers(Integer pageNum, Integer pageSize, String keyword, String role) {
        Page<User> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) {
            wrapper.and(w -> w.like(User::getUsername, keyword)
                    .or().like(User::getRealName, keyword)
                    .or().like(User::getPhone, keyword));
        }
        if (StringUtils.hasText(role)) {
            wrapper.eq(User::getRole, role);
        }
        wrapper.orderByDesc(User::getCreateTime);
        Page<User> result = this.page(page, wrapper);
        List<UserDTO> list = result.getRecords().stream().map(u -> {
            UserDTO dto = new UserDTO();
            BeanUtils.copyProperties(u, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = this.getById(id);
        if (user == null) throw new RuntimeException("用户不存在");
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto);
        return dto;
    }

    @Override
    public void createUser(UserDTO dto) {
        Long count = this.count(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (count > 0) throw new RuntimeException("用户名已存在");
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setStatus(1);
        this.save(user);
    }

    @Override
    public void updateUser(UserDTO dto) {
        User user = this.getById(dto.getId());
        if (user == null) throw new RuntimeException("用户不存在");
        if (StringUtils.hasText(dto.getRealName())) user.setRealName(dto.getRealName());
        if (StringUtils.hasText(dto.getEmail())) user.setEmail(dto.getEmail());
        if (StringUtils.hasText(dto.getPhone())) user.setPhone(dto.getPhone());
        if (StringUtils.hasText(dto.getRole())) user.setRole(dto.getRole());
        if (dto.getStatus() != null) user.setStatus(dto.getStatus());
        if (dto.getOrgId() != null) user.setOrgId(dto.getOrgId());
        this.updateById(user);
    }

    @Override
    public void deleteUser(Long id) { this.removeById(id); }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = this.getById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码错误");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        this.updateById(user);
    }

    @Override
    public void resetPassword(Long userId) {
        User user = this.getById(userId);
        user.setPassword(passwordEncoder.encode("123456"));
        this.updateById(user);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        User user = this.getById(id);
        user.setStatus(status);
        this.updateById(user);
    }
}
