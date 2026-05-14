package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.Certificate;
import com.exam.mapper.CertificateMapper;
import com.exam.service.CertificateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificateServiceImpl extends ServiceImpl<CertificateMapper, Certificate> implements CertificateService {

    @Override
    public List<Certificate> getByUserId(Long userId) {
        return this.list(new LambdaQueryWrapper<Certificate>()
                .eq(Certificate::getUserId, userId)
                .orderByDesc(Certificate::getCreateTime));
    }
}
