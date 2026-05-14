package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.KnowledgeDTO;
import com.exam.entity.Knowledge;

public interface KnowledgeService extends IService<Knowledge> {
    PageResult<KnowledgeDTO> listKnowledge(Integer pageNum, Integer pageSize, String keyword, Long categoryId, Long courseId);
    KnowledgeDTO getKnowledgeById(Long id);
    void createKnowledge(KnowledgeDTO dto);
    void updateKnowledge(KnowledgeDTO dto);
    void deleteKnowledge(Long id);
}
