package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.Certificate;

import java.util.List;

public interface CertificateService extends IService<Certificate> {
    List<Certificate> getByUserId(Long userId);
}
