package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.CourseCategoryDTO;
import com.exam.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course/category")
@RequiredArgsConstructor
public class CourseCategoryController {
    private final CourseCategoryService categoryService;

    @GetMapping("/list")
    public Result<PageResult<CourseCategoryDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(categoryService.listCategories(pageNum, pageSize, keyword));
    }

    @GetMapping("/all")
    public Result<List<CourseCategoryDTO>> listAll() {
        return Result.success(categoryService.listAllCategories());
    }

    @GetMapping("/{id}")
    public Result<CourseCategoryDTO> getById(@PathVariable Long id) {
        return Result.success(categoryService.getCategoryById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody CourseCategoryDTO dto) {
        categoryService.createCategory(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody CourseCategoryDTO dto) {
        categoryService.updateCategory(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }
}
