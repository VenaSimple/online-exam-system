package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.NoticeDTO;
import com.exam.entity.Notice;

public interface NoticeService extends IService<Notice> {
    PageResult<NoticeDTO> listNotices(Integer pageNum, Integer pageSize, String keyword, Long courseId);
    NoticeDTO getNoticeById(Long id);
    void createNotice(NoticeDTO dto);
    void updateNotice(NoticeDTO dto);
    void deleteNotice(Long id);
}
