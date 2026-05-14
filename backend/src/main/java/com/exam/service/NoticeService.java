package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.entity.Notice;

public interface NoticeService extends IService<Notice> {
    PageResult<Notice> listNotices(Integer pageNum, Integer pageSize, String keyword);
    void createNotice(Notice notice);
    void updateNotice(Notice notice);
    void deleteNotice(Long id);
}
