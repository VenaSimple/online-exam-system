package com.exam.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ExamDTO {
    private Long id;
    private String title;
    private Long paperId;
    private Integer examType;
    private Integer status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer duration;
    private Integer totalScore;
    private Integer passScore;
    private Integer maxAttempt;
    private Integer showAnswer;
    private Integer showAnalysis;
    private String participantIds;
    private String participantType;
}
