package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.CourseDTO;
import com.exam.service.CourseService;
import com.exam.service.CourseCategoryService;
import com.exam.entity.CourseCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseCategoryService categoryService;

    @GetMapping("/list")
    public Result<PageResult<CourseDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer status) {
        return Result.success(courseService.listCourses(pageNum, pageSize, keyword, categoryId, status));
    }

    @GetMapping("/student")
    public Result<PageResult<CourseDTO>> studentCourses(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(courseService.getStudentCourses(userId, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<CourseDTO> getById(@PathVariable Long id) {
        return Result.success(courseService.getCourseById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody CourseDTO dto) {
        courseService.createCourse(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CourseDTO dto) {
        courseService.updateCourse(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return Result.success();
    }

    @PutMapping("/{id}/status")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        courseService.updateStatus(id, status);
        return Result.success();
    }

    @GetMapping("/categories")
    public Result<List<CourseCategory>> listCategories() {
        return Result.success(categoryService.list());
    }

    @PostMapping("/categories")
    public Result<Void> createCategory(@RequestBody CourseCategory category) {
        categoryService.save(category);
        return Result.success();
    }

    @DeleteMapping("/categories/{id}")
    public Result<Void> deleteCategory(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }
}
