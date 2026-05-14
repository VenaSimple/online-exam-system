package com.exam.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.ExamSession;
import java.util.List;
public interface ExamSessionService extends IService<ExamSession> {
    List<ExamSession> getSessionsByExamId(Long examId);
    void addSession(ExamSession session);
    void updateSession(ExamSession session);
    void deleteSession(Long id);
}
