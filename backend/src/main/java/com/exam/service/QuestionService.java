package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.QuestionDTO;
import com.exam.entity.Question;
import java.util.List;

public interface QuestionService extends IService<Question> {
    PageResult<QuestionDTO> listQuestions(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer type, Integer difficulty);
    QuestionDTO getQuestionById(Long id);
    void createQuestion(QuestionDTO dto);
    void updateQuestion(QuestionDTO dto);
    void deleteQuestion(Long id);
    void batchDelete(List<Long> ids);
    void importQuestions(List<QuestionDTO> questions);
}
