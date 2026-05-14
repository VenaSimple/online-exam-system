package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.KnowledgeDTO;
import com.exam.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @GetMapping("/list")
    public Result<PageResult<KnowledgeDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long courseId) {
        return Result.success(knowledgeService.listKnowledge(pageNum, pageSize, keyword, categoryId, courseId));
    }

    @GetMapping("/{id}")
    public Result<KnowledgeDTO> getById(@PathVariable Long id) {
        return Result.success(knowledgeService.getKnowledgeById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody KnowledgeDTO dto) {
        knowledgeService.createKnowledge(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody KnowledgeDTO dto) {
        knowledgeService.updateKnowledge(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
        return Result.success();
    }
}
