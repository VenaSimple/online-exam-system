package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.ExamDTO;
import com.exam.dto.SubmitExamDTO;
import com.exam.entity.*;
import com.exam.mapper.*;
import com.exam.service.ExamService;
import com.exam.vo.ExamDetailVO;
import com.exam.vo.ExamResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    private final ExamPaperMapper paperMapper;
    private final ExamPaperQuestionMapper paperQuestionMapper;
    private final QuestionMapper questionMapper;
    private final ExamRecordMapper recordMapper;
    private final ExamAnswerMapper answerMapper;

    @Override
    public PageResult<ExamDTO> listExams(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        Page<Exam> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Exam::getTitle, keyword);
        if (status != null) wrapper.eq(Exam::getStatus, status);
        wrapper.orderByDesc(Exam::getCreateTime);
        Page<Exam> result = this.page(page, wrapper);
        List<ExamDTO> list = result.getRecords().stream().map(e -> {
            ExamDTO dto = new ExamDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public ExamDTO getExamById(Long id) {
        Exam exam = this.getById(id);
        if (exam == null) throw new RuntimeException("考试不存在");
        ExamDTO dto = new ExamDTO();
        BeanUtils.copyProperties(exam, dto);
        return dto;
    }

    @Override
    public void createExam(ExamDTO dto) {
        Exam exam = new Exam();
        BeanUtils.copyProperties(dto, exam);
        if (dto.getPaperId() != null) {
            ExamPaper paper = paperMapper.selectById(dto.getPaperId());
            if (paper != null) {
                exam.setPaperTitle(paper.getTitle());
                exam.setTotalScore(paper.getTotalScore());
                exam.setPassScore(paper.getPassScore());
                exam.setDuration(paper.getDuration());
            }
        }
        this.save(exam);
    }

    @Override
    public void updateExam(ExamDTO dto) {
        Exam exam = this.getById(dto.getId());
        if (exam == null) throw new RuntimeException("考试不存在");
        BeanUtils.copyProperties(dto, exam);
        this.updateById(exam);
    }

    @Override
    public void deleteExam(Long id) { this.removeById(id); }

    @Override
    public void publishExam(Long id) {
        Exam exam = this.getById(id);
        exam.setStatus(1);
        this.updateById(exam);
    }

    @Override
    public void endExam(Long id) {
        Exam exam = this.getById(id);
        exam.setStatus(3);
        this.updateById(exam);
    }

    @Override
    @Transactional
    public ExamDetailVO startExam(Long examId, Long userId) {
        Exam exam = this.getById(examId);
        if (exam == null) throw new RuntimeException("考试不存在");

        ExamRecord existingRecord = recordMapper.selectOne(
            new LambdaQueryWrapper<ExamRecord>()
                .eq(ExamRecord::getExamId, examId)
                .eq(ExamRecord::getUserId, userId)
                .eq(ExamRecord::getStatus, 1)
        );
        if (existingRecord != null) {
            return buildExamDetail(exam, existingRecord);
        }

        ExamRecord record = new ExamRecord();
        record.setExamId(examId);
        record.setExamTitle(exam.getTitle());
        record.setPaperId(exam.getPaperId());
        record.setUserId(userId);
        record.setTotalScore(exam.getTotalScore());
        record.setPassScore(exam.getPassScore());
        record.setStatus(1);
        record.setStartTime(LocalDateTime.now());
        recordMapper.insert(record);

        if (exam.getStatus() == 1) {
            exam.setStatus(2);
            this.updateById(exam);
        }

        return buildExamDetail(exam, record);
    }

    private ExamDetailVO buildExamDetail(Exam exam, ExamRecord record) {
        ExamDetailVO vo = new ExamDetailVO();
        vo.setExamId(exam.getId());
        vo.setExamTitle(exam.getTitle());
        vo.setPaperId(exam.getPaperId());
        vo.setDuration(exam.getDuration());
        vo.setTotalScore(exam.getTotalScore());
        vo.setPassScore(exam.getPassScore());
        vo.setStartTime(exam.getStartTime());
        vo.setEndTime(exam.getEndTime());
        vo.setShowAnswer(exam.getShowAnswer());

        List<ExamPaperQuestion> pqList = paperQuestionMapper.selectList(
            new LambdaQueryWrapper<ExamPaperQuestion>()
                .eq(ExamPaperQuestion::getPaperId, exam.getPaperId())
                .orderByAsc(ExamPaperQuestion::getSort)
        );
        List<ExamDetailVO.QuestionVO> questions = new ArrayList<>();
        for (ExamPaperQuestion pq : pqList) {
            Question q = questionMapper.selectById(pq.getQuestionId());
            if (q != null) {
                ExamDetailVO.QuestionVO qvo = new ExamDetailVO.QuestionVO();
                qvo.setQuestionId(q.getId());
                qvo.setQuestionType(q.getType());
                qvo.setQuestionContent(q.getContent());
                qvo.setOptions(q.getOptions());
                qvo.setScore(pq.getScore());
                qvo.setSort(pq.getSort());
                questions.add(qvo);
            }
        }
        vo.setQuestions(questions);
        return vo;
    }

    @Override
    @Transactional
    public ExamResultVO submitExam(SubmitExamDTO dto, Long userId) {
        Exam exam = this.getById(dto.getExamId());
        ExamRecord record = recordMapper.selectOne(
            new LambdaQueryWrapper<ExamRecord>()
                .eq(ExamRecord::getExamId, dto.getExamId())
                .eq(ExamRecord::getUserId, userId)
                .eq(ExamRecord::getStatus, 1)
        );
        if (record == null) throw new RuntimeException("未找到答题记录");

        int totalScore = 0;
        List<ExamResultVO.AnswerResultVO> answerResults = new ArrayList<>();

        for (SubmitExamDTO.AnswerItem item : dto.getAnswers()) {
            Question question = questionMapper.selectById(item.getQuestionId());
            ExamAnswer answer = new ExamAnswer();
            answer.setRecordId(record.getId());
            answer.setQuestionId(item.getQuestionId());
            answer.setQuestionType(question.getType());
            answer.setQuestionContent(question.getContent());
            answer.setOptions(question.getOptions());
            answer.setCorrectAnswer(question.getAnswer());
            answer.setUserAnswer(item.getUserAnswer());
            answer.setQuestionScore(getQuestionScore(exam, item.getQuestionId()));

            if (question.getType() <= 3) {
                boolean correct = question.getAnswer().equalsIgnoreCase(item.getUserAnswer());
                answer.setIsCorrect(correct ? 1 : 0);
                answer.setScore(correct ? answer.getQuestionScore() : 0);
                totalScore += answer.getScore();
            } else {
                answer.setIsCorrect(0);
                answer.setScore(0);
            }
            answer.setAnalysis(question.getAnalysis());
            answerMapper.insert(answer);

            ExamResultVO.AnswerResultVO ar = new ExamResultVO.AnswerResultVO();
            BeanUtils.copyProperties(answer, ar);
            answerResults.add(ar);
        }

        record.setUserScore(totalScore);
        record.setIsPass(totalScore >= record.getPassScore() ? 1 : 0);
        record.setStatus(2);
        record.setEndTime(LocalDateTime.now());
        record.setDuration((int) Duration.between(record.getStartTime(), record.getEndTime()).getSeconds());
        recordMapper.updateById(record);

        ExamResultVO resultVO = new ExamResultVO();
        resultVO.setRecordId(record.getId());
        resultVO.setUserScore(totalScore);
        resultVO.setTotalScore(record.getTotalScore());
        resultVO.setPassScore(record.getPassScore());
        resultVO.setIsPass(record.getIsPass());
        resultVO.setDuration(record.getDuration());
        resultVO.setAnswers(answerResults);
        return resultVO;
    }

    private Integer getQuestionScore(Exam exam, Long questionId) {
        ExamPaperQuestion pq = paperQuestionMapper.selectOne(
            new LambdaQueryWrapper<ExamPaperQuestion>()
                .eq(ExamPaperQuestion::getPaperId, exam.getPaperId())
                .eq(ExamPaperQuestion::getQuestionId, questionId)
        );
        return pq != null ? pq.getScore() : 0;
    }

    @Override
    public PageResult<ExamDTO> getStudentExams(Long userId, Integer pageNum, Integer pageSize) {
        Page<Exam> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Exam::getStatus, 1, 2);
        wrapper.orderByDesc(Exam::getCreateTime);
        Page<Exam> result = this.page(page, wrapper);
        List<ExamDTO> list = result.getRecords().stream().map(e -> {
            ExamDTO dto = new ExamDTO();
            BeanUtils.copyProperties(e, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public ExamResultVO getExamResult(Long recordId, Long userId) {
        ExamRecord record = recordMapper.selectById(recordId);
        if (record == null) throw new RuntimeException("记录不存在");
        List<ExamAnswer> answers = answerMapper.selectList(
            new LambdaQueryWrapper<ExamAnswer>().eq(ExamAnswer::getRecordId, recordId)
        );
        ExamResultVO vo = new ExamResultVO();
        vo.setRecordId(recordId);
        vo.setUserScore(record.getUserScore());
        vo.setTotalScore(record.getTotalScore());
        vo.setPassScore(record.getPassScore());
        vo.setIsPass(record.getIsPass());
        vo.setDuration(record.getDuration());
        List<ExamResultVO.AnswerResultVO> arList = answers.stream().map(a -> {
            ExamResultVO.AnswerResultVO ar = new ExamResultVO.AnswerResultVO();
            BeanUtils.copyProperties(a, ar);
            return ar;
        }).collect(Collectors.toList());
        vo.setAnswers(arList);
        return vo;
    }

    @Override
    public PageResult<ExamRecord> getExamRecords(Long examId, Integer pageNum, Integer pageSize) {
        Page<ExamRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ExamRecord> wrapper = new LambdaQueryWrapper<>();
        if (examId != null) wrapper.eq(ExamRecord::getExamId, examId);
        wrapper.orderByDesc(ExamRecord::getCreateTime);
        Page<ExamRecord> result = recordMapper.selectPage(page, wrapper);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }
}
