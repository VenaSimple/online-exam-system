package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.CourseDTO;
import com.exam.entity.Course;
import com.exam.entity.CourseCategory;
import com.exam.entity.StudentCourse;
import com.exam.mapper.CourseCategoryMapper;
import com.exam.mapper.CourseMapper;
import com.exam.mapper.StudentCourseMapper;
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

    private final CourseCategoryMapper courseCategoryMapper;
    private final StudentCourseMapper studentCourseMapper;

    @Override
    public PageResult<CourseDTO> listCourses(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer status) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Course::getTitle, keyword);
        if (categoryId != null) wrapper.eq(Course::getCategoryId, categoryId);
        if (status != null) wrapper.eq(Course::getStatus, status);
        wrapper.orderByDesc(Course::getCreateTime);
        Page<Course> result = this.page(page, wrapper);
        List<CourseDTO> list = result.getRecords().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public CourseDTO getCourseById(Long id) {
        Course course = this.getById(id);
        if (course == null) throw new RuntimeException("课程不存在");
        return toDTO(course);
    }

    @Override
    public void createCourse(CourseDTO dto) {
        Course course = new Course();
        BeanUtils.copyProperties(dto, course);
        course.setStudentCount(0);
        course.setViewCount(0);
        course.setAvgScore(BigDecimal.ZERO);
        if (course.getStatus() == null) course.setStatus(0);
        this.save(course);
    }

    @Override
    public void updateCourse(CourseDTO dto) {
        Course course = this.getById(dto.getId());
        if (course == null) throw new RuntimeException("课程不存在");
        // 仅更新DTO中非null的字段，避免覆盖统计数据
        if (dto.getTitle() != null) course.setTitle(dto.getTitle());
        if (dto.getCover() != null) course.setCover(dto.getCover());
        if (dto.getDescription() != null) course.setDescription(dto.getDescription());
        if (dto.getCategoryId() != null) course.setCategoryId(dto.getCategoryId());
        if (dto.getTeacherId() != null) course.setTeacherId(dto.getTeacherId());
        if (dto.getTeacherName() != null) course.setTeacherName(dto.getTeacherName());
        if (dto.getPrice() != null) course.setPrice(dto.getPrice());
        if (dto.getOriginalPrice() != null) course.setOriginalPrice(dto.getOriginalPrice());
        if (dto.getStatus() != null) course.setStatus(dto.getStatus());
        if (dto.getIsFree() != null) course.setIsFree(dto.getIsFree());
        if (dto.getTags() != null) course.setTags(dto.getTags());
        this.updateById(course);
    }

    @Override
    public void deleteCourse(Long id) { this.removeById(id); }

    @Override
    public void updateStatus(Long id, Integer status) {
        Course course = this.getById(id);
        if (course == null) throw new RuntimeException("课程不存在");
        course.setStatus(status);
        this.updateById(course);
    }

    @Override
    public PageResult<CourseDTO> getStudentCourses(Long userId, Integer pageNum, Integer pageSize) {
        // 查出该学生已选的课程ID列表
        LambdaQueryWrapper<StudentCourse> scWrapper = new LambdaQueryWrapper<>();
        scWrapper.eq(StudentCourse::getUserId, userId);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(scWrapper);
        List<Long> courseIds = studentCourses.stream()
                .map(StudentCourse::getCourseId)
                .collect(Collectors.toList());

        if (courseIds.isEmpty()) {
            return new PageResult<>(0L, List.of());
        }

        Page<Course> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Course> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Course::getId, courseIds);
        wrapper.eq(Course::getStatus, 1);
        wrapper.orderByDesc(Course::getStudentCount);
        Page<Course> result = this.page(page, wrapper);
        List<CourseDTO> list = result.getRecords().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    /**
     * Entity -> DTO，自动回填分类名称
     */
    private CourseDTO toDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        BeanUtils.copyProperties(course, dto);
        // 回填分类名称
        if (course.getCategoryId() != null) {
            CourseCategory cat = courseCategoryMapper.selectById(course.getCategoryId());
            if (cat != null) dto.setCategoryName(cat.getName());
        }
        return dto;
    }
}
