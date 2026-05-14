package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.ExamPaperDTO;
import com.exam.entity.ExamPaper;

public interface ExamPaperService extends IService<ExamPaper> {
    PageResult<ExamPaperDTO> listPapers(Integer pageNum, Integer pageSize, String keyword);
    ExamPaperDTO getPaperById(Long id);
    void createPaper(ExamPaperDTO dto);
    void updatePaper(ExamPaperDTO dto);
    void deletePaper(Long id);
}
