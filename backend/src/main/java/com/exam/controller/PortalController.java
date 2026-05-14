package com.exam.controller;
import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.CourseDTO;
import com.exam.dto.NewsDTO;
import com.exam.dto.NoticeDTO;
import com.exam.entity.Banner;
import com.exam.service.BannerService;
import com.exam.service.CourseService;
import com.exam.service.NewsService;
import com.exam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class PortalController {
    private final BannerService bannerService;
    private final CourseService courseService;
    private final NewsService newsService;
    private final NoticeService noticeService;
    @GetMapping("/banners")
    public Result<List<Banner>> banners() { return Result.success(bannerService.getActiveBanners()); }
    @GetMapping("/courses")
    public Result<PageResult<CourseDTO>> courses(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "12") Integer pageSize) {
        return Result.success(courseService.listCourses(pageNum, pageSize, null, null, 1));
    }
    @GetMapping("/news")
    public Result<PageResult<NewsDTO>> news(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(newsService.listNews(pageNum, pageSize, null));
    }
    @GetMapping("/notices")
    public Result<PageResult<NoticeDTO>> notices(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(noticeService.listNotices(pageNum, pageSize, null, null));
    }
}
