package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.entity.News;

public interface NewsService extends IService<News> {
    PageResult<News> listNews(Integer pageNum, Integer pageSize, String keyword);
    void createNews(News news);
    void updateNews(News news);
    void deleteNews(Long id);
}
