package com.exam.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.exam.entity.Permission;
import com.exam.entity.RolePermission;
import java.util.List;
public interface PermissionService extends IService<Permission> {
    List<Permission> getPermissionTree();
    List<Permission> getPermissionsByRole(String role);
    void assignPermissions(String role, List<Long> permissionIds);
}
