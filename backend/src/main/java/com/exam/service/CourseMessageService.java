package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.dto.CourseMessageDTO;
import com.exam.entity.CourseMessage;

import java.util.List;

public interface CourseMessageService extends IService<CourseMessage> {
    List<CourseMessageDTO> getMessagesByCourseId(Long courseId);
    void createMessage(CourseMessageDTO dto, Long userId, String username);
    void deleteMessage(Long id);
    void updateStatus(Long id, Integer status);
}
