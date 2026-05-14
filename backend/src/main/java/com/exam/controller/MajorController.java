package com.exam.controller;
import com.exam.common.Result;
import com.exam.entity.Major;
import com.exam.service.MajorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/major")
@RequiredArgsConstructor
public class MajorController {
    private final MajorService majorService;
    @GetMapping("/list")
    public Result<List<Major>> list() { return Result.success(majorService.listMajors()); }
    @PostMapping
    public Result<Void> create(@RequestBody Major major) { majorService.createMajor(major); return Result.success(); }
    @PutMapping
    public Result<Void> update(@RequestBody Major major) { majorService.updateMajor(major); return Result.success(); }
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) { majorService.deleteMajor(id); return Result.success(); }
}
