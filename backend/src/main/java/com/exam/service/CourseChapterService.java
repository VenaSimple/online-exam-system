package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.dto.CourseChapterDTO;
import com.exam.entity.CourseChapter;

import java.util.List;

public interface CourseChapterService extends IService<CourseChapter> {
    List<CourseChapterDTO> getChaptersByCourseId(Long courseId);
    void createChapter(CourseChapterDTO dto);
    void updateChapter(CourseChapterDTO dto);
    void deleteChapter(Long id);
}
