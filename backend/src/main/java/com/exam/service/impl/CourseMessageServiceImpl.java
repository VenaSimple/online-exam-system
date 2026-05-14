package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.dto.CourseMessageDTO;
import com.exam.entity.CourseMessage;
import com.exam.mapper.CourseMessageMapper;
import com.exam.service.CourseMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseMessageServiceImpl extends ServiceImpl<CourseMessageMapper, CourseMessage> implements CourseMessageService {

    @Override
    public List<CourseMessageDTO> getMessagesByCourseId(Long courseId) {
        LambdaQueryWrapper<CourseMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CourseMessage::getCourseId, courseId);
        wrapper.eq(CourseMessage::getStatus, 1);
        wrapper.orderByDesc(CourseMessage::getCreateTime);
        List<CourseMessage> list = this.list(wrapper);
        return list.stream().map(m -> {
            CourseMessageDTO dto = new CourseMessageDTO();
            BeanUtils.copyProperties(m, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createMessage(CourseMessageDTO dto, Long userId, String username) {
        CourseMessage msg = new CourseMessage();
        BeanUtils.copyProperties(dto, msg);
        msg.setUserId(userId);
        msg.setUserName(username);
        this.save(msg);
    }

    @Override
    public void deleteMessage(Long id) {
        this.removeById(id);
    }

    @Override
    public void updateStatus(Long id, Integer status) {
        CourseMessage msg = this.getById(id);
        if (msg != null) {
            msg.setStatus(status);
            this.updateById(msg);
        }
    }
}
