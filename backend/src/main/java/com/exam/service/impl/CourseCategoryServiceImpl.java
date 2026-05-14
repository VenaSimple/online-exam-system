package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.CourseCategoryDTO;
import com.exam.entity.CourseCategory;
import com.exam.mapper.CourseCategoryMapper;
import com.exam.service.CourseCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Override
    public PageResult<CourseCategoryDTO> listCategories(Integer pageNum, Integer pageSize, String keyword) {
        Page<CourseCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CourseCategory> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(CourseCategory::getName, keyword);
        wrapper.orderByAsc(CourseCategory::getSort);
        Page<CourseCategory> result = this.page(page, wrapper);
        List<CourseCategoryDTO> list = result.getRecords().stream().map(c -> {
            CourseCategoryDTO dto = new CourseCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public List<CourseCategoryDTO> listAllCategories() {
        List<CourseCategory> list = this.list(new LambdaQueryWrapper<CourseCategory>().orderByAsc(CourseCategory::getSort));
        return list.stream().map(c -> {
            CourseCategoryDTO dto = new CourseCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CourseCategoryDTO getCategoryById(Long id) {
        CourseCategory entity = this.getById(id);
        if (entity == null) throw new RuntimeException("分类不存在");
        CourseCategoryDTO dto = new CourseCategoryDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void createCategory(CourseCategoryDTO dto) {
        CourseCategory entity = new CourseCategory();
        BeanUtils.copyProperties(dto, entity);
        this.save(entity);
    }

    @Override
    public void updateCategory(CourseCategoryDTO dto) {
        CourseCategory entity = this.getById(dto.getId());
        if (entity == null) throw new RuntimeException("分类不存在");
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void deleteCategory(Long id) {
        this.removeById(id);
    }
}
