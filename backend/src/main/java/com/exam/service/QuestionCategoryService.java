package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.QuestionCategoryDTO;
import com.exam.entity.QuestionCategory;

import java.util.List;

public interface QuestionCategoryService extends IService<QuestionCategory> {
    PageResult<QuestionCategoryDTO> listCategories(Integer pageNum, Integer pageSize, String keyword);
    List<QuestionCategoryDTO> listAllCategories();
    QuestionCategoryDTO getCategoryById(Long id);
    void createCategory(QuestionCategoryDTO dto);
    void updateCategory(QuestionCategoryDTO dto);
    void deleteCategory(Long id);
}
