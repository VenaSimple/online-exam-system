package com.exam.vo;

import lombok.Data;
import java.util.List;

@Data
public class ExamResultVO {
    private Long recordId;
    private Integer userScore;
    private Integer totalScore;
    private Integer passScore;
    private Integer isPass;
    private Integer duration;
    private List<AnswerResultVO> answers;

    @Data
    public static class AnswerResultVO {
        private Long questionId;
        private String questionContent;
        private String options;
        private String correctAnswer;
        private String userAnswer;
        private Integer isCorrect;
        private Integer score;
        private Integer questionScore;
        private String analysis;
    }
}
