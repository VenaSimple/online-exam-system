package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.CourseDTO;
import com.exam.entity.Course;

public interface CourseService extends IService<Course> {
    PageResult<CourseDTO> listCourses(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status);
    CourseDTO getCourseById(Long id);
    void createCourse(CourseDTO dto);
    void updateCourse(CourseDTO dto);
    void deleteCourse(Long id);
    void updateStatus(Long id, Integer status);
    PageResult<CourseDTO> getStudentCourses(Long userId, Integer pageNum, Integer pageSize);
}
