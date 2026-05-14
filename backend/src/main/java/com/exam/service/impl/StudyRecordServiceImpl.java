package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.entity.StudyRecord;
import com.exam.mapper.StudyRecordMapper;
import com.exam.service.StudyRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyRecordServiceImpl extends ServiceImpl<StudyRecordMapper, StudyRecord> implements StudyRecordService {

    @Override
    public void updateProgress(Long userId, Long courseId, Long chapterId, Integer progress, Integer duration, Integer lastPosition) {
        StudyRecord record = this.getOne(new LambdaQueryWrapper<StudyRecord>()
            .eq(StudyRecord::getUserId, userId)
            .eq(StudyRecord::getCourseId, courseId)
            .eq(StudyRecord::getChapterId, chapterId));
        if (record == null) {
            record = new StudyRecord();
            record.setUserId(userId);
            record.setCourseId(courseId);
            record.setChapterId(chapterId);
            record.setProgress(progress);
            record.setDuration(duration);
            record.setLastPosition(lastPosition);
            this.save(record);
        } else {
            record.setProgress(Math.max(record.getProgress(), progress));
            record.setDuration(record.getDuration() + duration);
            record.setLastPosition(lastPosition);
            this.updateById(record);
        }
    }

    @Override
    public PageResult<StudyRecord> getUserStudyRecords(Long userId, Integer pageNum, Integer pageSize) {
        Page<StudyRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<StudyRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StudyRecord::getUserId, userId);
        wrapper.orderByDesc(StudyRecord::getUpdateTime);
        Page<StudyRecord> result = this.page(page, wrapper);
        return new PageResult<>(result.getTotal(), result.getRecords());
    }

    @Override
    public StudyRecord getStudyDetail(Long userId, Long courseId) {
        return this.getOne(new LambdaQueryWrapper<StudyRecord>()
            .eq(StudyRecord::getUserId, userId)
            .eq(StudyRecord::getCourseId, courseId)
            .last("LIMIT 1"));
    }
}
