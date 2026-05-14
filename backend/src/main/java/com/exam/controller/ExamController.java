package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.ExamDTO;
import com.exam.dto.SubmitExamDTO;
import com.exam.entity.ExamRecord;
import com.exam.service.ExamService;
import com.exam.vo.ExamDetailVO;
import com.exam.vo.ExamResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @GetMapping("/list")
    public Result<PageResult<ExamDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer status) {
        return Result.success(examService.listExams(pageNum, pageSize, keyword, status));
    }

    @GetMapping("/student")
    public Result<PageResult<ExamDTO>> studentExams(
            Authentication authentication,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(examService.getStudentExams(userId, pageNum, pageSize));
    }

    @GetMapping("/{id}")
    public Result<ExamDTO> getById(@PathVariable Long id) {
        return Result.success(examService.getExamById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody ExamDTO dto) {
        examService.createExam(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ExamDTO dto) {
        examService.updateExam(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        examService.deleteExam(id);
        return Result.success();
    }

    @PutMapping("/{id}/publish")
    public Result<Void> publish(@PathVariable Long id) {
        examService.publishExam(id);
        return Result.success();
    }

    @PutMapping("/{id}/end")
    public Result<Void> end(@PathVariable Long id) {
        examService.endExam(id);
        return Result.success();
    }

    @PostMapping("/{examId}/start")
    public Result<ExamDetailVO> startExam(@PathVariable Long examId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(examService.startExam(examId, userId));
    }

    @PostMapping("/submit")
    public Result<ExamResultVO> submitExam(@RequestBody SubmitExamDTO dto, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(examService.submitExam(dto, userId));
    }

    @GetMapping("/result/{recordId}")
    public Result<ExamResultVO> getResult(@PathVariable Long recordId, Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(examService.getExamResult(recordId, userId));
    }

    @GetMapping("/records")
    public Result<PageResult<ExamRecord>> getRecords(
            @RequestParam(required = false) Long examId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(examService.getExamRecords(examId, pageNum, pageSize));
    }
}
