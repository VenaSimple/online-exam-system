package com.exam.controller;

import com.exam.common.Result;
import com.exam.dto.CourseChapterDTO;
import com.exam.service.CourseChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/chapter")
@RequiredArgsConstructor
public class CourseChapterController {

    private final CourseChapterService chapterService;

    @GetMapping("/list/{courseId}")
    public Result<List<CourseChapterDTO>> listByCourseId(@PathVariable Long courseId) {
        return Result.success(chapterService.getChaptersByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public Result<CourseChapterDTO> getById(@PathVariable Long id) {
        CourseChapterDTO dto = new CourseChapterDTO();
        BeanUtils.copyProperties(chapterService.getById(id), dto);
        return Result.success(dto);
    }

    @PostMapping
    public Result<Void> create(@RequestBody CourseChapterDTO dto) {
        chapterService.createChapter(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CourseChapterDTO dto) {
        chapterService.updateChapter(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return Result.success();
    }
}
