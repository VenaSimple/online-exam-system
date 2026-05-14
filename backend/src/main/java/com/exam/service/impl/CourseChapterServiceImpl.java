package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.CourseChapter;
import com.exam.mapper.CourseChapterMapper;
import com.exam.service.CourseChapterService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements CourseChapterService {

    @Override
    public List<CourseChapter> getChaptersByCourseId(Long courseId) {
        return this.list(new LambdaQueryWrapper<CourseChapter>()
                .eq(CourseChapter::getCourseId, courseId)
                .orderByAsc(CourseChapter::getSort));
    }
}
