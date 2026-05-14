package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.ExamPaperDTO;
import com.exam.entity.ExamPaper;
import com.exam.entity.ExamPaperQuestion;
import com.exam.mapper.ExamPaperMapper;
import com.exam.mapper.ExamPaperQuestionMapper;
import com.exam.service.ExamPaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements ExamPaperService {

    private final ExamPaperQuestionMapper paperQuestionMapper;

    @Override
    public PageResult<ExamPaperDTO> listPapers(Integer pageNum, Integer pageSize, String keyword) {
        Page<ExamPaper> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<ExamPaper> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(ExamPaper::getTitle, keyword);
        wrapper.orderByDesc(ExamPaper::getCreateTime);
        Page<ExamPaper> result = this.page(page, wrapper);
        List<ExamPaperDTO> list = result.getRecords().stream().map(p -> {
            ExamPaperDTO dto = new ExamPaperDTO();
            BeanUtils.copyProperties(p, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public ExamPaperDTO getPaperById(Long id) {
        ExamPaper paper = this.getById(id);
        if (paper == null) throw new RuntimeException("试卷不存在");
        ExamPaperDTO dto = new ExamPaperDTO();
        BeanUtils.copyProperties(paper, dto);
        List<ExamPaperQuestion> pqList = paperQuestionMapper.selectList(
            new LambdaQueryWrapper<ExamPaperQuestion>().eq(ExamPaperQuestion::getPaperId, id).orderByAsc(ExamPaperQuestion::getSort)
        );
        List<ExamPaperDTO.PaperQuestionItem> items = pqList.stream().map(pq -> {
            ExamPaperDTO.PaperQuestionItem item = new ExamPaperDTO.PaperQuestionItem();
            item.setQuestionId(pq.getQuestionId());
            item.setSort(pq.getSort());
            item.setScore(pq.getScore());
            return item;
        }).collect(Collectors.toList());
        dto.setQuestions(items);
        return dto;
    }

    @Override
    @Transactional
    public void createPaper(ExamPaperDTO dto) {
        ExamPaper paper = new ExamPaper();
        BeanUtils.copyProperties(dto, paper);
        paper.setQuestionCount(dto.getQuestions() != null ? dto.getQuestions().size() : 0);
        this.save(paper);
        savePaperQuestions(paper.getId(), dto.getQuestions());
    }

    @Override
    @Transactional
    public void updatePaper(ExamPaperDTO dto) {
        ExamPaper paper = this.getById(dto.getId());
        if (paper == null) throw new RuntimeException("试卷不存在");
        BeanUtils.copyProperties(dto, paper);
        paper.setQuestionCount(dto.getQuestions() != null ? dto.getQuestions().size() : 0);
        this.updateById(paper);
        paperQuestionMapper.delete(new LambdaQueryWrapper<ExamPaperQuestion>().eq(ExamPaperQuestion::getPaperId, dto.getId()));
        savePaperQuestions(dto.getId(), dto.getQuestions());
    }

    @Override
    public void deletePaper(Long id) {
        this.removeById(id);
        paperQuestionMapper.delete(new LambdaQueryWrapper<ExamPaperQuestion>().eq(ExamPaperQuestion::getPaperId, id));
    }

    private void savePaperQuestions(Long paperId, List<ExamPaperDTO.PaperQuestionItem> questions) {
        if (questions != null) {
            for (ExamPaperDTO.PaperQuestionItem item : questions) {
                ExamPaperQuestion pq = new ExamPaperQuestion();
                pq.setPaperId(paperId);
                pq.setQuestionId(item.getQuestionId());
                pq.setSort(item.getSort());
                pq.setScore(item.getScore());
                paperQuestionMapper.insert(pq);
            }
        }
    }
}
