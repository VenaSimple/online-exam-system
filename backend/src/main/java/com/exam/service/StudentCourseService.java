package com.exam.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.entity.StudentCourse;
import java.util.List;
public interface StudentCourseService extends IService<StudentCourse> {
    void assignCourse(Long userId, Long courseId, Long assignBy);
    void batchAssign(List<Long> userIds, Long courseId, Long assignBy);
    PageResult<StudentCourse> getStudentCourses(Long userId, Integer pageNum, Integer pageSize);
    List<StudentCourse> getCourseStudents(Long courseId);
    void updateProgress(Long userId, Long courseId, Integer progress);
}
