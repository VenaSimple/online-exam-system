package com.exam.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.Major;
import com.exam.mapper.MajorMapper;
import com.exam.service.MajorService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MajorServiceImpl extends ServiceImpl<MajorMapper, Major> implements MajorService {
    @Override
    public List<Major> listMajors() { return list(new LambdaQueryWrapper<Major>().orderByAsc(Major::getSort)); }
    @Override
    public void createMajor(Major major) { save(major); }
    @Override
    public void updateMajor(Major major) { updateById(major); }
    @Override
    public void deleteMajor(Long id) { removeById(id); }
}
