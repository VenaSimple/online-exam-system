package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.entity.Knowledge;
import com.exam.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/knowledge")
@RequiredArgsConstructor
public class KnowledgeController {

    private final KnowledgeService knowledgeService;

    @GetMapping("/list")
    public Result<PageResult<Knowledge>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        return Result.success(knowledgeService.listKnowledge(pageNum, pageSize, keyword, categoryId));
    }

    @GetMapping("/{id}")
    public Result<Knowledge> getById(@PathVariable Long id) {
        return Result.success(knowledgeService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody Knowledge knowledge) {
        knowledgeService.createKnowledge(knowledge);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Knowledge knowledge) {
        knowledgeService.updateKnowledge(knowledge);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        knowledgeService.deleteKnowledge(id);
        return Result.success();
    }
}
