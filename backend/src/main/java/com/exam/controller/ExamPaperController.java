package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.ExamPaperDTO;
import com.exam.service.ExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paper")
@RequiredArgsConstructor
public class ExamPaperController {

    private final ExamPaperService paperService;

    @GetMapping("/list")
    public Result<PageResult<ExamPaperDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(paperService.listPapers(pageNum, pageSize, keyword));
    }

    @GetMapping("/{id}")
    public Result<ExamPaperDTO> getById(@PathVariable Long id) {
        return Result.success(paperService.getPaperById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody ExamPaperDTO dto) {
        paperService.createPaper(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody ExamPaperDTO dto) {
        paperService.updatePaper(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        paperService.deletePaper(id);
        return Result.success();
    }
}
