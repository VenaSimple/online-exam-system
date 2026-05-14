package com.exam.dto;

import lombok.Data;
import java.util.List;

@Data
public class SubmitExamDTO {
    private Long examId;
    private List<AnswerItem> answers;

    @Data
    public static class AnswerItem {
        private Long questionId;
        private Integer questionType;
        private String userAnswer;
    }
}
