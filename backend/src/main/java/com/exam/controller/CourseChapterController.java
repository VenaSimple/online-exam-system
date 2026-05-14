package com.exam.controller;

import com.exam.common.Result;
import com.exam.entity.CourseChapter;
import com.exam.service.CourseChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/chapter")
@RequiredArgsConstructor
public class CourseChapterController {

    private final CourseChapterService chapterService;

    @GetMapping("/list/{courseId}")
    public Result<List<CourseChapter>> listByCourseId(@PathVariable Long courseId) {
        return Result.success(chapterService.getChaptersByCourseId(courseId));
    }

    @GetMapping("/{id}")
    public Result<CourseChapter> getById(@PathVariable Long id) {
        return Result.success(chapterService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody CourseChapter chapter) {
        chapterService.save(chapter);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CourseChapter chapter) {
        chapterService.updateById(chapter);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.success();
    }
}
