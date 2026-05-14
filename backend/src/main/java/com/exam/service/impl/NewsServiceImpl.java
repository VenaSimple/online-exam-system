package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.NewsDTO;
import com.exam.entity.News;
import com.exam.mapper.NewsMapper;
import com.exam.service.NewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public PageResult<NewsDTO> listNews(Integer pageNum, Integer pageSize, String keyword) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(News::getTitle, keyword);
        wrapper.orderByDesc(News::getCreateTime);
        Page<News> result = this.page(page, wrapper);
        List<NewsDTO> list = result.getRecords().stream().map(n -> {
            NewsDTO dto = new NewsDTO();
            BeanUtils.copyProperties(n, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public NewsDTO getNewsById(Long id) {
        News entity = this.getById(id);
        if (entity == null) throw new RuntimeException("新闻不存在");
        NewsDTO dto = new NewsDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void createNews(NewsDTO dto) {
        News entity = new News();
        BeanUtils.copyProperties(dto, entity);
        this.save(entity);
    }

    @Override
    public void updateNews(NewsDTO dto) {
        News entity = this.getById(dto.getId());
        if (entity == null) throw new RuntimeException("新闻不存在");
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void deleteNews(Long id) {
        this.removeById(id);
    }
}
