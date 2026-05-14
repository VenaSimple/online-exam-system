package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.QuestionDTO;
import com.exam.entity.Question;
import com.exam.mapper.QuestionMapper;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Override
    public PageResult<QuestionDTO> listQuestions(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Integer type, Integer difficulty) {
        Page<Question> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Question::getContent, keyword);
        if (categoryId != null) wrapper.eq(Question::getCategoryId, categoryId);
        if (type != null) wrapper.eq(Question::getType, type);
        if (difficulty != null) wrapper.eq(Question::getDifficulty, difficulty);
        wrapper.orderByDesc(Question::getCreateTime);
        Page<Question> result = this.page(page, wrapper);
        List<QuestionDTO> list = result.getRecords().stream().map(q -> {
            QuestionDTO dto = new QuestionDTO();
            BeanUtils.copyProperties(q, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        Question q = this.getById(id);
        if (q == null) throw new RuntimeException("题目不存在");
        QuestionDTO dto = new QuestionDTO();
        BeanUtils.copyProperties(q, dto);
        return dto;
    }

    @Override
    public void createQuestion(QuestionDTO dto) {
        Question q = new Question();
        BeanUtils.copyProperties(dto, q);
        this.save(q);
    }

    @Override
    public void updateQuestion(QuestionDTO dto) {
        Question q = this.getById(dto.getId());
        if (q == null) throw new RuntimeException("题目不存在");
        BeanUtils.copyProperties(dto, q);
        this.updateById(q);
    }

    @Override
    public void deleteQuestion(Long id) { this.removeById(id); }

    @Override
    public void batchDelete(List<Long> ids) { this.removeByIds(ids); }

    @Override
    public void importQuestions(List<QuestionDTO> questions) {
        for (QuestionDTO dto : questions) {
            createQuestion(dto);
        }
    }
}
