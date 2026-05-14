package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.QuestionDTO;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public Result<PageResult<QuestionDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer difficulty) {
        return Result.success(questionService.listQuestions(pageNum, pageSize, keyword, categoryId, type, difficulty));
    }

    @GetMapping("/{id}")
    public Result<QuestionDTO> getById(@PathVariable Long id) {
        return Result.success(questionService.getQuestionById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody QuestionDTO dto) {
        questionService.createQuestion(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody QuestionDTO dto) {
        questionService.updateQuestion(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return Result.success();
    }

    @DeleteMapping("/batch")
    public Result<Void> batchDelete(@RequestBody List<Long> ids) {
        questionService.batchDelete(ids);
        return Result.success();
    }

    @PostMapping("/import")
    public Result<Void> importQuestions(@RequestBody List<QuestionDTO> questions) {
        questionService.importQuestions(questions);
        return Result.success();
    }
}
