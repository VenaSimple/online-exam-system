package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.CourseDTO;
import com.exam.entity.Course;
import com.exam.mapper.CourseMapper;
import com.exam.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Override
    public PageResult<CourseDTO> listCourses(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Course::getTitle, keyword);
        if (categoryId != null) wrapper.eq(Course::getCategoryId, categoryId);
        if (status != null) wrapper.eq(Course::getStatus, status);
        wrapper.orderByDesc(Course::getCreateTime);
        Page<Course> result = this.page(page, wrapper);
        List<CourseDTO> list = result.getRecords().stream().map(c -> {
            CourseDTO dto = new CourseDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = this.getById(id);
        if (course == null) throw new RuntimeException("课程不存在");
        CourseDTO dto = new CourseDTO();
        BeanUtils.copyProperties(course, dto);
        return dto;
    }

    @Override
    public void createCourse(CourseDTO dto) {
        Course course = new Course();
        BeanUtils.copyProperties(dto, course);
        course.setStudentCount(0);
        course.setViewCount(0);
        course.setAvgScore(BigDecimal.ZERO);
        this.save(course);
    }

    @Override
    public void updateCourse(CourseDTO dto) {
        Course course = this.getById(dto.getId());
        if (course == null) throw new RuntimeException("课程不存在");
        BeanUtils.copyProperties(dto, course);
        this.updateById(course);
    }

    @Override
    public void deleteCourse(Long id) { this.removeById(id); }

    @Override
    public void updateStatus(Long id, Integer status) {
        Course course = this.getById(id);
        course.setStatus(status);
        this.updateById(course);
    }

    @Override
    public PageResult<CourseDTO> getStudentCourses(Long userId, Integer pageNum, Integer pageSize) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Course::getStatus, 1);
        wrapper.orderByDesc(Course::getStudentCount);
        Page<Course> result = this.page(page, wrapper);
        List<CourseDTO> list = result.getRecords().stream().map(c -> {
            CourseDTO dto = new CourseDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }
}
