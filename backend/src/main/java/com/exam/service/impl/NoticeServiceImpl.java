package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.NoticeDTO;
import com.exam.entity.Notice;
import com.exam.mapper.NoticeMapper;
import com.exam.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Override
    public PageResult<NoticeDTO> listNotices(Integer pageNum, Integer pageSize, String keyword, Integer status) {
        Page<Notice> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Notice> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Notice::getTitle, keyword);
        if (status != null) wrapper.eq(Notice::getStatus, status);
        wrapper.orderByDesc(Notice::getCreateTime);
        Page<Notice> result = this.page(page, wrapper);
        List<NoticeDTO> list = result.getRecords().stream().map(n -> {
            NoticeDTO dto = new NoticeDTO();
            BeanUtils.copyProperties(n, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public NoticeDTO getNoticeById(Long id) {
        Notice notice = this.getById(id);
        if (notice == null) throw new RuntimeException("公告不存在");
        NoticeDTO dto = new NoticeDTO();
        BeanUtils.copyProperties(notice, dto);
        return dto;
    }

    @Override
    public void createNotice(NoticeDTO dto) {
        Notice notice = new Notice();
        BeanUtils.copyProperties(dto, notice);
        this.save(notice);
    }

    @Override
    public void updateNotice(NoticeDTO dto) {
        Notice notice = this.getById(dto.getId());
        if (notice == null) throw new RuntimeException("公告不存在");
        BeanUtils.copyProperties(dto, notice);
        this.updateById(notice);
    }

    @Override
    public void deleteNotice(Long id) { this.removeById(id); }

    @Override
    public void updateStatus(Long id, Integer status) {
        Notice notice = this.getById(id);
        notice.setStatus(status);
        this.updateById(notice);
    }
}
