package com.exam.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.entity.StudentCourse;
import com.exam.mapper.StudentCourseMapper;
import com.exam.service.StudentCourseService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class StudentCourseServiceImpl extends ServiceImpl<StudentCourseMapper, StudentCourse> implements StudentCourseService {
    @Override
    public void assignCourse(Long userId, Long courseId, Long assignBy) {
        long count = count(new LambdaQueryWrapper<StudentCourse>().eq(StudentCourse::getUserId, userId).eq(StudentCourse::getCourseId, courseId));
        if (count > 0) throw new RuntimeException("该学员已开通此课程");
        StudentCourse sc = new StudentCourse();
        sc.setUserId(userId);
        sc.setCourseId(courseId);
        sc.setProgress(0);
        sc.setStatus(1);
        sc.setAssignBy(assignBy);
        save(sc);
    }
    @Override
    public void batchAssign(List<Long> userIds, Long courseId, Long assignBy) {
        for (Long userId : userIds) { assignCourse(userId, courseId, assignBy); }
    }
    @Override
    public PageResult<StudentCourse> getStudentCourses(Long userId, Integer pageNum, Integer pageSize) {
        Page<StudentCourse> page = new Page<>(pageNum, pageSize);
        Page<StudentCourse> result = page(page, new LambdaQueryWrapper<StudentCourse>().eq(StudentCourse::getUserId, userId).orderByDesc(StudentCourse::getCreateTime));
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
    @Override
    public List<StudentCourse> getCourseStudents(Long courseId) {
        return list(new LambdaQueryWrapper<StudentCourse>().eq(StudentCourse::getCourseId, courseId));
    }
    @Override
    public void updateProgress(Long userId, Long courseId, Integer progress) {
        StudentCourse sc = getOne(new LambdaQueryWrapper<StudentCourse>().eq(StudentCourse::getUserId, userId).eq(StudentCourse::getCourseId, courseId));
        if (sc != null) { sc.setProgress(progress); updateById(sc); }
    }
}
