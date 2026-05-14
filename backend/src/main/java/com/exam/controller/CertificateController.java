package com.exam.controller;

import com.exam.common.Result;
import com.exam.entity.Certificate;
import com.exam.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/my")
    public Result<?> myCertificates(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        return Result.success(certificateService.getByUserId(userId));
    }

    @GetMapping("/{id}")
    public Result<Certificate> getById(@PathVariable Long id) {
        return Result.success(certificateService.getById(id));
    }
}
