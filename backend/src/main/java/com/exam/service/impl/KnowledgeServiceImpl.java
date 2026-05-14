package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.KnowledgeDTO;
import com.exam.entity.Knowledge;
import com.exam.mapper.KnowledgeMapper;
import com.exam.service.KnowledgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Override
    public PageResult<KnowledgeDTO> listKnowledges(Integer pageNum, Integer pageSize, String keyword, Long categoryId) {
        Page<Knowledge> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Knowledge::getTitle, keyword);
        if (categoryId != null) wrapper.eq(Knowledge::getCategoryId, categoryId);
        wrapper.orderByDesc(Knowledge::getCreateTime);
        Page<Knowledge> result = this.page(page, wrapper);
        List<KnowledgeDTO> list = result.getRecords().stream().map(k -> {
            KnowledgeDTO dto = new KnowledgeDTO();
            BeanUtils.copyProperties(k, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public KnowledgeDTO getKnowledgeById(Long id) {
        Knowledge knowledge = this.getById(id);
        if (knowledge == null) throw new RuntimeException("知识点不存在");
        KnowledgeDTO dto = new KnowledgeDTO();
        BeanUtils.copyProperties(knowledge, dto);
        return dto;
    }

    @Override
    public void createKnowledge(KnowledgeDTO dto) {
        Knowledge knowledge = new Knowledge();
        BeanUtils.copyProperties(dto, knowledge);
        this.save(knowledge);
    }

    @Override
    public void updateKnowledge(KnowledgeDTO dto) {
        Knowledge knowledge = this.getById(dto.getId());
        if (knowledge == null) throw new RuntimeException("知识点不存在");
        BeanUtils.copyProperties(dto, knowledge);
        this.updateById(knowledge);
    }

    @Override
    public void deleteKnowledge(Long id) { this.removeById(id); }
}
