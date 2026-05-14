package com.exam.vo;

import lombok.Data;

@Data
public class DashboardVO {
    private Long userCount;
    private Long courseCount;
    private Long examCount;
    private Long questionCount;
    private Long todayStudyCount;
    private Long todayExamCount;
}
