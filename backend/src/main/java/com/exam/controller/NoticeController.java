package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.NoticeDTO;
import com.exam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public Result<PageResult<NoticeDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword) {
        return Result.success(noticeService.listNotices(pageNum, pageSize, keyword));
    }

    @GetMapping("/{id}")
    public Result<NoticeDTO> getById(@PathVariable Long id) {
        return Result.success(noticeService.getNoticeById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody NoticeDTO dto) {
        noticeService.createNotice(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody NoticeDTO dto) {
        noticeService.updateNotice(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        noticeService.deleteNotice(id);
        return Result.success();
    }
}
