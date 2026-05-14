package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.NewsDTO;
import com.exam.entity.News;
import com.exam.mapper.NewsMapper;
import com.exam.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public PageResult<NewsDTO> listNews(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        Page<News> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<News> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(News::getTitle, keyword);
        if (status != null) wrapper.eq(News::getStatus, status);
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
        News news = this.getById(id);
        if (news == null) throw new RuntimeException("新闻不存在");
        NewsDTO dto = new NewsDTO();
        BeanUtils.copyProperties(news, dto);
        return dto;
    }

    @Override
    public void createNews(NewsDTO dto) {
        News news = new News();
        BeanUtils.copyProperties(dto, news);
        this.save(news);
    }

    @Override
    public void updateNews(NewsDTO dto) {
        News news = this.getById(dto.getId());
        if (news == null) throw new RuntimeException("新闻不存在");
        BeanUtils.copyProperties(dto, news);
        this.updateById(news);
    }

    @Override
    public void deleteNews(Long id) { this.removeById(id); }

    @Override
    public void updateStatus(Long id, Integer status) {
        News news = this.getById(id);
        news.setStatus(status);
        this.updateById(news);
    }
}
