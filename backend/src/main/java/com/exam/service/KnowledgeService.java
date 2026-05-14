package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.entity.Knowledge;

public interface KnowledgeService extends IService<Knowledge> {
    PageResult<Knowledge> listKnowledge(Integer pageNum, Integer pageSize, String keyword, Long categoryId);
    void createKnowledge(Knowledge knowledge);
    void updateKnowledge(Knowledge knowledge);
    void deleteKnowledge(Long id);
}
