package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.entity.News;
import com.exam.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/list")
    public Result<PageResult<News>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(newsService.listNews(pageNum, pageSize, keyword));
    }

    @GetMapping("/{id}")
    public Result<News> getById(@PathVariable Long id) {
        return Result.success(newsService.getById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody News news) {
        newsService.createNews(news);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody News news) {
        newsService.updateNews(news);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        newsService.deleteNews(id);
        return Result.success();
    }
}
