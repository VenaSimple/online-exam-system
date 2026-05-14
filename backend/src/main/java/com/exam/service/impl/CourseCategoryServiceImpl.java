package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.CourseCategoryDTO;
import com.exam.entity.CourseCategory;
import com.exam.mapper.CourseCategoryMapper;
import com.exam.service.CourseCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

    @Override
    public PageResult<CourseCategoryDTO> listCategories(Integer pageNum, Integer pageSize) {
        Page<CourseCategory> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<CourseCategory> wrapper = new LambdaQueryWrapper<>();
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
        List<CourseCategory> categories = this.list(new LambdaQueryWrapper<CourseCategory>().orderByAsc(CourseCategory::getSort));
        return categories.stream().map(c -> {
            CourseCategoryDTO dto = new CourseCategoryDTO();
            BeanUtils.copyProperties(c, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public CourseCategoryDTO getCategoryById(Long id) {
        CourseCategory category = this.getById(id);
        if (category == null) throw new RuntimeException("课程分类不存在");
        CourseCategoryDTO dto = new CourseCategoryDTO();
        BeanUtils.copyProperties(category, dto);
        return dto;
    }

    @Override
    public void createCategory(CourseCategoryDTO dto) {
        CourseCategory category = new CourseCategory();
        BeanUtils.copyProperties(dto, category);
        this.save(category);
    }

    @Override
    public void updateCategory(CourseCategoryDTO dto) {
        CourseCategory category = this.getById(dto.getId());
        if (category == null) throw new RuntimeException("课程分类不存在");
        BeanUtils.copyProperties(dto, category);
        this.updateById(category);
    }

    @Override
    public void deleteCategory(Long id) { this.removeById(id); }
}
