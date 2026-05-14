package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.dto.CourseChapterDTO;
import com.exam.entity.CourseChapter;
import com.exam.mapper.CourseChapterMapper;
import com.exam.service.CourseChapterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseChapterServiceImpl extends ServiceImpl<CourseChapterMapper, CourseChapter> implements CourseChapterService {

    @Override
    public List<CourseChapterDTO> getChaptersByCourseId(Long courseId) {
        List<CourseChapter> list = this.list(new LambdaQueryWrapper<CourseChapter>()
                .eq(CourseChapter::getCourseId, courseId)
                .orderByAsc(CourseChapter::getSort));
        return list.stream().map(c -> {
            CourseChapterDTO dto = new CourseChapterDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createChapter(CourseChapterDTO dto) {
        CourseChapter chapter = new CourseChapter();
        BeanUtils.copyProperties(dto, chapter);
        this.save(chapter);
    }

    @Override
    public void updateChapter(CourseChapterDTO dto) {
        CourseChapter chapter = this.getById(dto.getId());
        if (chapter == null) throw new RuntimeException("章节不存在");
        if (dto.getTitle() != null) chapter.setTitle(dto.getTitle());
        if (dto.getParentId() != null) chapter.setParentId(dto.getParentId());
        if (dto.getSort() != null) chapter.setSort(dto.getSort());
        if (dto.getType() != null) chapter.setType(dto.getType());
        if (dto.getMediaUrl() != null) chapter.setMediaUrl(dto.getMediaUrl());
        if (dto.getDuration() != null) chapter.setDuration(dto.getDuration());
        if (dto.getIsFree() != null) chapter.setIsFree(dto.getIsFree());
        this.updateById(chapter);
    }

    @Override
    public void deleteChapter(Long id) {
        this.removeById(id);
    }
}
