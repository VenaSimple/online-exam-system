package com.exam.controller;

import com.exam.common.Result;
import com.exam.entity.QuestionCategory;
import com.exam.service.QuestionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/category")
@RequiredArgsConstructor
public class QuestionCategoryController {

    private final QuestionCategoryService categoryService;

    @GetMapping("/list")
    public Result<List<QuestionCategory>> list() {
        return Result.success(categoryService.list());
    }

    @PostMapping
    public Result<Void> create(@RequestBody QuestionCategory category) {
        categoryService.save(category);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody QuestionCategory category) {
        categoryService.updateById(category);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.removeById(id);
        return Result.success();
    }
}
