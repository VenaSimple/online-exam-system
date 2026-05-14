package com.exam.dto;

import lombok.Data;
import java.util.List;

@Data
public class ExamPaperDTO {
    private Long id;
    private String title;
    private String description;
    private Integer totalScore;
    private Integer passScore;
    private Integer duration;
    private Integer type;
    private Long categoryId;
    private Integer status;
    private List<PaperQuestionItem> questions;

    @Data
    public static class PaperQuestionItem {
        private Long questionId;
        private Integer sort;
        private Integer score;
    }
}
