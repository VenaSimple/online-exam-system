package com.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.common.PageResult;
import com.exam.dto.OrganizationDTO;
import com.exam.entity.Organization;
import com.exam.mapper.OrganizationMapper;
import com.exam.service.OrganizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl extends ServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public List<OrganizationDTO> getOrgTree() {
        List<Organization> list = this.list(new LambdaQueryWrapper<Organization>().orderByAsc(Organization::getSort));
        return list.stream().map(o -> {
            OrganizationDTO dto = new OrganizationDTO();
            BeanUtils.copyProperties(o, dto);
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public PageResult<OrganizationDTO> listOrganizations(Integer pageNum, Integer pageSize, String keyword, Integer type) {
        Page<Organization> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<Organization> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(keyword)) wrapper.like(Organization::getName, keyword);
        if (type != null) wrapper.eq(Organization::getType, type);
        wrapper.orderByAsc(Organization::getSort);
        Page<Organization> result = this.page(page, wrapper);
        List<OrganizationDTO> list = result.getRecords().stream().map(o -> {
            OrganizationDTO dto = new OrganizationDTO();
            BeanUtils.copyProperties(o, dto);
            return dto;
        }).collect(Collectors.toList());
        return new PageResult<>(result.getTotal(), list);
    }

    @Override
    public OrganizationDTO getOrganizationById(Long id) {
        Organization entity = this.getById(id);
        if (entity == null) throw new RuntimeException("机构不存在");
        OrganizationDTO dto = new OrganizationDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public void createOrg(OrganizationDTO dto) {
        Organization entity = new Organization();
        BeanUtils.copyProperties(dto, entity);
        this.save(entity);
    }

    @Override
    public void updateOrg(OrganizationDTO dto) {
        Organization entity = this.getById(dto.getId());
        if (entity == null) throw new RuntimeException("机构不存在");
        BeanUtils.copyProperties(dto, entity);
        this.updateById(entity);
    }

    @Override
    public void deleteOrg(Long id) {
        this.removeById(id);
    }
}
