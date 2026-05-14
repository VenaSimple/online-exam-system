package com.exam.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.ExamSession;
import com.exam.mapper.ExamSessionMapper;
import com.exam.service.ExamSessionService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ExamSessionServiceImpl extends ServiceImpl<ExamSessionMapper, ExamSession> implements ExamSessionService {
    @Override
    public List<ExamSession> getSessionsByExamId(Long examId) {
        return list(new LambdaQueryWrapper<ExamSession>().eq(ExamSession::getExamId, examId).orderByAsc(ExamSession::getStartTime));
    }
    @Override
    public void addSession(ExamSession session) { save(session); }
    @Override
    public void updateSession(ExamSession session) { updateById(session); }
    @Override
    public void deleteSession(Long id) { removeById(id); }
}
