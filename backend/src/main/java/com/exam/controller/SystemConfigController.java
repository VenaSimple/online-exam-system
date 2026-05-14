package com.exam.controller;
import com.exam.common.Result;
import com.exam.entity.SystemConfig;
import com.exam.service.SystemConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class SystemConfigController {
    private final SystemConfigService configService;
    @GetMapping("/get")
    public Result<String> get(@RequestParam String key) { return Result.success(configService.getConfig(key)); }
    @PostMapping("/set")
    public Result<Void> set(@RequestParam String key, @RequestParam String value) { configService.setConfig(key, value); return Result.success(); }
    @GetMapping("/group")
    public Result<Map<String, List<SystemConfig>>> getByGroup() { return Result.success(configService.getConfigByGroup()); }
    @GetMapping("/list")
    public Result<List<SystemConfig>> list() { return Result.success(configService.list()); }
    @PutMapping
    public Result<Void> update(@RequestBody SystemConfig config) { configService.updateById(config); return Result.success(); }
}
