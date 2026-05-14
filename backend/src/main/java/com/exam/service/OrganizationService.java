package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.common.PageResult;
import com.exam.dto.OrganizationDTO;
import com.exam.entity.Organization;

import java.util.List;

public interface OrganizationService extends IService<Organization> {
    List<OrganizationDTO> getOrgTree();
    PageResult<OrganizationDTO> listOrganizations(Integer pageNum, Integer pageSize, String keyword, Integer type);
    OrganizationDTO getOrganizationById(Long id);
    void createOrg(OrganizationDTO dto);
    void updateOrg(OrganizationDTO dto);
    void deleteOrg(Long id);
}
