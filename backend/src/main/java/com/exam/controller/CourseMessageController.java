package com.exam.controller;

import com.exam.common.Result;
import com.exam.dto.CourseMessageDTO;
import com.exam.service.CourseMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/message")
@RequiredArgsConstructor
public class CourseMessageController {

    private final CourseMessageService messageService;

    @GetMapping("/list/{courseId}")
    public Result<List<CourseMessageDTO>> listByCourseId(@PathVariable Long courseId) {
        return Result.success(messageService.getMessagesByCourseId(courseId));
    }

    @PostMapping
    public Result<Void> create(@RequestBody CourseMessageDTO dto, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        String username = authentication.getName();
        messageService.createMessage(dto, userId, username);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        messageService.deleteMessage(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        messageService.updateStatus(id, status);
        return Result.success();
    }
}
