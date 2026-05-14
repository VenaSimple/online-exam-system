package com.exam.controller;

import com.exam.common.Result;
import com.exam.entity.CourseReview;
import com.exam.service.CourseReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/review")
@RequiredArgsConstructor
public class CourseReviewController {

    private final CourseReviewService reviewService;

    @PostMapping
    public Result<Void> create(@RequestBody CourseReview review) {
        reviewService.save(review);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<?> list(@RequestParam Long courseId) {
        return Result.success(reviewService.list(
            new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<CourseReview>()
                .eq(CourseReview::getCourseId, courseId)
                .orderByDesc(CourseReview::getCreateTime)));
    }
}
