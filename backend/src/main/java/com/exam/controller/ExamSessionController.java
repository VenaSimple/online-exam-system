package com.exam.controller;
import com.exam.common.Result;
import com.exam.entity.ExamSession;
import com.exam.service.ExamSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/exam/session")
@RequiredArgsConstructor
public class ExamSessionController {
    private final ExamSessionService sessionService;
    @GetMapping("/list/{examId}")
    public Result<List<ExamSession>> listByExam(@PathVariable Long examId) { return Result.success(sessionService.getSessionsByExamId(examId)); }
    @PostMapping
    public Result<Void> add(@RequestBody ExamSession session) { sessionService.addSession(session); return Result.success(); }
    @PutMapping
    public Result<Void> update(@RequestBody ExamSession session) { sessionService.updateSession(session); return Result.success(); }
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) { sessionService.deleteSession(id); return Result.success(); }
}
