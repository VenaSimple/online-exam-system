package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.entity.StudyRecord;

public interface StudyRecordService extends IService<StudyRecord> {
    void updateProgress(Long userId, Long courseId, Long chapterId, Integer progress, Integer duration, Integer lastPosition);
    PageResult<StudyRecord> getUserStudyRecords(Long userId, Integer pageNum, Integer pageSize);
    StudyRecord getStudyDetail(Long userId, Long courseId);
}
