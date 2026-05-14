package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.NewsDTO;
import com.exam.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/list")
    public Result<PageResult<NewsDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(newsService.listNews(pageNum, pageSize, keyword));
    }

    @GetMapping("/{id}")
    public Result<NewsDTO> getById(@PathVariable Long id) {
        return Result.success(newsService.getNewsById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody NewsDTO dto) {
        newsService.createNews(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody NewsDTO dto) {
        newsService.updateNews(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        newsService.deleteNews(id);
        return Result.success();
    }
}
