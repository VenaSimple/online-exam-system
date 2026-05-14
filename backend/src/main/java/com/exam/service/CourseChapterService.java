package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.CourseChapter;

import java.util.List;

public interface CourseChapterService extends IService<CourseChapter> {
    List<CourseChapter> getChaptersByCourseId(Long courseId);
}
