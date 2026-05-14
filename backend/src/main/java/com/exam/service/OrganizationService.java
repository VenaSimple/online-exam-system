package com.exam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.Organization;
import java.util.List;

public interface OrganizationService extends IService<Organization> {
    List<Organization> getOrgTree();
    void createOrg(Organization org);
    void updateOrg(Organization org);
    void deleteOrg(Long id);
}
