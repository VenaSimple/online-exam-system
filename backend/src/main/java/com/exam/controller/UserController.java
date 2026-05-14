package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.ChangePasswordDTO;
import com.exam.dto.UserDTO;
import com.exam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public Result<PageResult<UserDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String role) {
        return Result.success(userService.listUsers(pageNum, pageSize, keyword, role));
    }

    @GetMapping("/{id}")
    public Result<UserDTO> getById(@PathVariable Long id) {
        return Result.success(userService.getUserById(id));
    }

    @GetMapping("/info")
    public Result<UserDTO> getUserInfo(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(userService.getUserById(userId));
    }

    @PostMapping
    public Result<Void> create(@RequestBody UserDTO dto) {
        userService.createUser(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody UserDTO dto) {
        userService.updateUser(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success();
    }

    @PutMapping("/changePassword")
    public Result<Void> changePassword(@RequestBody ChangePasswordDTO dto, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        userService.changePassword(userId, dto.getOldPassword(), dto.getNewPassword());
        return Result.success();
    }

    @PutMapping("/{id}/resetPassword")
    public Result<Void> resetPassword(@PathVariable Long id) {
        userService.resetPassword(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        userService.updateStatus(id, status);
        return Result.success();
    }
}
