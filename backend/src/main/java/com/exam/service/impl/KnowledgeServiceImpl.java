package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.KnowledgeDTO;
import com.exam.entity.Knowledge;
import com.exam.mapper.KnowledgeMapper;
import com.exam.service.KnowledgeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

    @Override
    public PageResult<KnowledgeDTO> listKnowledge(Integer pageNum, Integer pageSize, String keyword, Long categoryId) {
        Page<Knowledge> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Knowledge> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Knowledge::getTitle, keyword);
        if (categoryId != null) wrapper.eq(Knowledge::getCategoryId, categoryId);
        wrapper.eq(Knowledge::getStatus, 1);
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
        Knowledge entity = this.getById(id);
        if (entity == null) throw new RuntimeException("知识不存在");
        KnowledgeDTO dto = new KnowledgeDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void createKnowledge(KnowledgeDTO dto) {
        Knowledge entity = new Knowledge();
        BeanUtils.copyProperties(dto, entity);
        this.save(entity);
    }

    @Override
    public void updateKnowledge(KnowledgeDTO dto) {
        Knowledge entity = this.getById(dto.getId());
        if (entity == null) throw new RuntimeException("知识不存在");
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void deleteKnowledge(Long id) {
        this.removeById(id);
    }
}
