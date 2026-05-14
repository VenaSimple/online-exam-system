package com.exam.vo;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ExamDetailVO {
    private Long examId;
    private String examTitle;
    private Long paperId;
    private Integer duration;
    private Integer totalScore;
    private Integer passScore;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer showAnswer;
    private List<QuestionVO> questions;

    @Data
    public static class QuestionVO {
        private Long questionId;
        private Integer questionType;
        private String questionContent;
        private String options;
        private Integer score;
        private Integer sort;
    }
}
