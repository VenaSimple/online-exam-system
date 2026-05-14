package com.exam.controller;

import com.exam.common.Result;
import com.exam.service.UserService;
import com.exam.service.CourseService;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import com.exam.vo.DashboardVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final CourseService courseService;
    private final ExamService examService;
    private final QuestionService questionService;

    @GetMapping("/stats")
    public Result<DashboardVO> getStats() {
        DashboardVO vo = new DashboardVO();
        vo.setUserCount(userService.count());
        vo.setCourseCount(courseService.count());
        vo.setExamCount(examService.count());
        vo.setQuestionCount(questionService.count());
        vo.setTodayStudyCount(0L);
        vo.setTodayExamCount(0L);
        return Result.success(vo);
    }
}
