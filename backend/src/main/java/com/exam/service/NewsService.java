package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.NewsDTO;
import com.exam.entity.News;

public interface NewsService extends IService<News> {
    PageResult<NewsDTO> listNews(Integer pageNum, Integer pageSize, String keyword);
    NewsDTO getNewsById(Long id);
    void createNews(NewsDTO dto);
    void updateNews(NewsDTO dto);
    void deleteNews(Long id);
}
