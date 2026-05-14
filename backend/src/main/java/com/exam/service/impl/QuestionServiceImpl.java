package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.QuestionDTO;
import com.exam.entity.Question;
import com.exam.entity.QuestionCategory;
import com.exam.mapper.QuestionCategoryMapper;
import com.exam.mapper.QuestionMapper;
import com.exam.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    private final QuestionCategoryMapper questionCategoryMapper;

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
        List<QuestionDTO> list = result.getRecords().stream().map(this::toDTO).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public QuestionDTO getQuestionById(Long id) {
        Question q = this.getById(id);
        if (q == null) throw new RuntimeException("题目不存在");
        return toDTO(q);
    }

    @Override
    public void createQuestion(QuestionDTO dto) {
        Question q = new Question();
        BeanUtils.copyProperties(dto, q);
        // 自动填充分类名称
        fillCategoryName(q);
        this.save(q);
        // 更新分类下题目数量
        incrementCategoryCount(q.getCategoryId());
    }

    @Override
    public void updateQuestion(QuestionDTO dto) {
        Question q = this.getById(dto.getId());
        if (q == null) throw new RuntimeException("题目不存在");
        Long oldCategoryId = q.getCategoryId();
        BeanUtils.copyProperties(dto, q);
        // 自动填充分类名称
        fillCategoryName(q);
        this.updateById(q);
        // 如果分类变更，更新两个分类的题目数量
        if (dto.getCategoryId() != null && !dto.getCategoryId().equals(oldCategoryId)) {
            decrementCategoryCount(oldCategoryId);
            incrementCategoryCount(dto.getCategoryId());
        }
    }

    @Override
    public void deleteQuestion(Long id) {
        Question q = this.getById(id);
        if (q != null) {
            this.removeById(id);
            decrementCategoryCount(q.getCategoryId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            deleteQuestion(id);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importQuestions(List<QuestionDTO> questions) {
        for (QuestionDTO dto : questions) {
            createQuestion(dto);
        }
    }

    /**
     * Entity -> DTO，自动回填分类名称
     */
    private QuestionDTO toDTO(Question q) {
        QuestionDTO dto = new QuestionDTO();
        BeanUtils.copyProperties(q, dto);
        // 如果实体没有categoryName，从分类表回填
        if (q.getCategoryId() != null && !StringUtils.hasText(q.getCategoryName())) {
            QuestionCategory cat = questionCategoryMapper.selectById(q.getCategoryId());
            if (cat != null) dto.setCategoryName(cat.getName());
        }
        return dto;
    }

    /**
     * 根据 categoryId 自动填充 categoryName
     */
    private void fillCategoryName(Question q) {
        if (q.getCategoryId() != null) {
            QuestionCategory cat = questionCategoryMapper.selectById(q.getCategoryId());
            if (cat != null) {
                q.setCategoryName(cat.getName());
            }
        }
    }

    /**
     * 增加分类下题目数量
     */
    private void incrementCategoryCount(Long categoryId) {
        if (categoryId == null) return;
        QuestionCategory cat = questionCategoryMapper.selectById(categoryId);
        if (cat != null) {
            cat.setQuestionCount(cat.getQuestionCount() == null ? 1 : cat.getQuestionCount() + 1);
            questionCategoryMapper.updateById(cat);
        }
    }

    /**
     * 减少分类下题目数量
     */
    private void decrementCategoryCount(Long categoryId) {
        if (categoryId == null) return;
        QuestionCategory cat = questionCategoryMapper.selectById(categoryId);
        if (cat != null && cat.getQuestionCount() != null && cat.getQuestionCount() > 0) {
            cat.setQuestionCount(cat.getQuestionCount() - 1);
            questionCategoryMapper.updateById(cat);
        }
    }
}
