package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.QuestionCategoryDTO;
import com.exam.entity.QuestionCategory;
import com.exam.mapper.QuestionCategoryMapper;
import com.exam.service.QuestionCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionCategoryServiceImpl extends ServiceImpl<QuestionCategoryMapper, QuestionCategory> implements QuestionCategoryService {

    @Override
    public PageResult<QuestionCategoryDTO> listCategories(Integer pageNum, Integer pageSize) {
        Page<QuestionCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<QuestionCategory> wrapper = new LambdaQueryWrapper<>();
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
        List<QuestionCategory> categories = this.list(new LambdaQueryWrapper<QuestionCategory>().orderByAsc(QuestionCategory::getSort));
        return categories.stream().map(c -> {
            QuestionCategoryDTO dto = new QuestionCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public QuestionCategoryDTO getCategoryById(Long id) {
        QuestionCategory category = this.getById(id);
        if (category == null) throw new RuntimeException("题目分类不存在");
        QuestionCategoryDTO dto = new QuestionCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    @Override
    public void createCategory(QuestionCategoryDTO dto) {
        QuestionCategory category = new QuestionCategory();
        BeanUtils.copyProperties(dto, category);
        this.save(category);
    }

    @Override
    public void updateCategory(QuestionCategoryDTO dto) {
        QuestionCategory category = this.getById(dto.getId());
        if (category == null) throw new RuntimeException("题目分类不存在");
        BeanUtils.copyProperties(dto, category);
        this.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) { this.removeById(id); }
}
