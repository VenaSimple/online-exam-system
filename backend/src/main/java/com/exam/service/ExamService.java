package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.ExamDTO;
import com.exam.dto.SubmitExamDTO;
import com.exam.entity.Exam;
import com.exam.entity.ExamRecord;
import com.exam.vo.ExamDetailVO;
import com.exam.vo.ExamResultVO;

public interface ExamService extends IService<Exam> {
    PageResult<ExamDTO> listExams(Integer pageNum, Integer pageSize, String keyword, Integer status, Long courseId);
    ExamDTO getExamById(Long id);
    void createExam(ExamDTO dto);
    void updateExam(ExamDTO dto);
    void deleteExam(Long id);
    void publishExam(Long id);
    void endExam(Long id);
    ExamDetailVO startExam(Long examId, Long userId);
    ExamResultVO submitExam(SubmitExamDTO dto, Long userId);
    PageResult<ExamDTO> getStudentExams(Long userId, Integer pageNum, Integer pageSize);
    ExamResultVO getExamResult(Long recordId, Long userId);
    PageResult<ExamRecord> getExamRecords(Long examId, Integer pageNum, Integer pageSize);
}
