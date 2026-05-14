package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.QuestionCategoryDTO;
import com.exam.entity.Question;
import com.exam.entity.QuestionCategory;
import com.exam.mapper.QuestionCategoryMapper;
import com.exam.mapper.QuestionMapper;
import com.exam.service.QuestionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionCategoryServiceImpl extends ServiceImpl<QuestionCategoryMapper, QuestionCategory> implements QuestionCategoryService {

    private final QuestionMapper questionMapper;

    @Override
    public PageResult<QuestionCategoryDTO> listCategories(Integer pageNum, Integer pageSize, String keyword) {
        Page<QuestionCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<QuestionCategory> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(QuestionCategory::getName, keyword);
        wrapper.orderByAsc(QuestionCategory::getSort);
        Page<QuestionCategory> result = this.page(page, wrapper);
        List<QuestionCategoryDTO> list = result.getRecords().stream().map(c -> {
            QuestionCategoryDTO dto = new QuestionCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public List<QuestionCategoryDTO> listAllCategories() {
        List<QuestionCategory> list = this.list(new LambdaQueryWrapper<QuestionCategory>().orderByAsc(QuestionCategory::getSort));
        return list.stream().map(c -> {
            QuestionCategoryDTO dto = new QuestionCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public QuestionCategoryDTO getCategoryById(Long id) {
        QuestionCategory entity = this.getById(id);
        if (entity == null) throw new RuntimeException("分类不存在");
        QuestionCategoryDTO dto = new QuestionCategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void createCategory(QuestionCategoryDTO dto) {
        QuestionCategory entity = new QuestionCategory();
        BeanUtils.copyProperties(dto, entity);
        entity.setQuestionCount(0);
        this.save(entity);
    }

    @Override
    public void updateCategory(QuestionCategoryDTO dto) {
        QuestionCategory entity = this.getById(dto.getId());
        if (entity == null) throw new RuntimeException("分类不存在");
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void deleteCategory(Long id) {
        // 检查是否有子分类
        long childCount = this.count(new LambdaQueryWrapper<QuestionCategory>().eq(QuestionCategory::getParentId, id));
        if (childCount > 0) throw new RuntimeException("该分类下存在子分类，无法删除");
        // 检查是否有题目引用
        long questionCount = questionMapper.selectCount(new LambdaQueryWrapper<Question>().eq(Question::getCategoryId, id));
        if (questionCount > 0) throw new RuntimeException("该分类下存在题目，无法删除");
        this.removeById(id);
    }
}
