package com.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.CourseReview;
import com.exam.mapper.CourseReviewMapper;
import com.exam.service.CourseReviewService;
import org.springframework.stereotype.Service;

@Service
public class CourseReviewServiceImpl extends ServiceImpl<CourseReviewMapper, CourseReview> implements CourseReviewService {
}
