package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.entity.StudyRecord;
import com.exam.service.StudyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyRecordController {

    private final StudyRecordService studyRecordService;

    @PostMapping("/progress")
    public Result<Void> updateProgress(
            Authentication authentication,
            @RequestParam Long courseId,
            @RequestParam Long chapterId,
            @RequestParam Integer progress,
            @RequestParam(defaultValue = "0") Integer duration,
            @RequestParam(defaultValue = "0") Integer lastPosition) {
        Long userId = (Long) authentication.getPrincipal();
        studyRecordService.updateProgress(userId, courseId, chapterId, progress, duration, lastPosition);
        return Result.success();
    }

    @GetMapping("/records")
    public Result<PageResult<StudyRecord>> getRecords(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(studyRecordService.getUserStudyRecords(userId, pageNum, pageSize));
    }

    @GetMapping("/detail")
    public Result<StudyRecord> getDetail(
            Authentication authentication,
            @RequestParam Long courseId) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(studyRecordService.getStudyDetail(userId, courseId));
    }
}
