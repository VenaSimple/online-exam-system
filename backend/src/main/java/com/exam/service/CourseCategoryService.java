package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.CourseCategoryDTO;
import com.exam.entity.CourseCategory;

import java.util.List;

public interface CourseCategoryService extends IService<CourseCategory> {
    PageResult<CourseCategoryDTO> listCategories(Integer pageNum, Integer pageSize, String keyword);
    List<CourseCategoryDTO> listAllCategories();
    CourseCategoryDTO getCategoryById(Long id);
    void createCategory(CourseCategoryDTO dto);
    void updateCategory(CourseCategoryDTO dto);
    void deleteCategory(Long id);
}
