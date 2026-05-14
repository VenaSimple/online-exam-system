package com.exam.controller;
import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.entity.StudentCourse;
import com.exam.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/student-course")
@RequiredArgsConstructor
public class StudentCourseController {
    private final StudentCourseService studentCourseService;
    @PostMapping("/assign")
    public Result<Void> assign(@RequestParam Long userId, @RequestParam Long courseId, Authentication auth) {
        Long assignBy = (Long) auth.getPrincipal();
        studentCourseService.assignCourse(userId, courseId, assignBy);
        return Result.success();
    }
    @PostMapping("/batch-assign")
    public Result<Void> batchAssign(@RequestBody List<Long> userIds, @RequestParam Long courseId, Authentication auth) {
        Long assignBy = (Long) auth.getPrincipal();
        studentCourseService.batchAssign(userIds, courseId, assignBy);
        return Result.success();
    }
    @GetMapping("/my-courses")
    public Result<PageResult<StudentCourse>> myCourses(Authentication auth, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) auth.getPrincipal();
        return Result.success(studentCourseService.getStudentCourses(userId, pageNum, pageSize));
    }
    @GetMapping("/course-students/{courseId}")
    public Result<List<StudentCourse>> courseStudents(@PathVariable Long courseId) {
        return Result.success(studentCourseService.getCourseStudents(courseId));
    }
}
