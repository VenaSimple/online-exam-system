package com.exam.controller;
import com.exam.common.Result;
import com.exam.entity.Banner;
import com.exam.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/banner")
@RequiredArgsConstructor
public class BannerController {
    private final BannerService bannerService;
    @GetMapping("/active")
    public Result<List<Banner>> getActive() { return Result.success(bannerService.getActiveBanners()); }
    @GetMapping("/list")
    public Result<List<Banner>> list() { return Result.success(bannerService.list()); }
    @PostMapping
    public Result<Void> create(@RequestBody Banner banner) { bannerService.save(banner); return Result.success(); }
    @PutMapping
    public Result<Void> update(@RequestBody Banner banner) { bannerService.updateById(banner); return Result.success(); }
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) { bannerService.removeById(id); return Result.success(); }
}
