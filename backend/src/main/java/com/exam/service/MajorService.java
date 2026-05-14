package com.exam.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.Major;
import java.util.List;
public interface MajorService extends IService<Major> {
    List<Major> listMajors();
    void createMajor(Major major);
    void updateMajor(Major major);
    void deleteMajor(Long id);
}
