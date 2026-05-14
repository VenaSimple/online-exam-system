package com.exam.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.exam.entity.Permission;
import com.exam.entity.RolePermission;
import com.exam.mapper.PermissionMapper;
import com.exam.mapper.RolePermissionMapper;
import com.exam.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    private final RolePermissionMapper rolePermissionMapper;
    @Override
    public List<Permission> getPermissionTree() {
        return list(new LambdaQueryWrapper<Permission>().orderByAsc(Permission::getSort));
    }
    @Override
    public List<Permission> getPermissionsByRole(String role) {
        List<RolePermission> rps = rolePermissionMapper.selectList(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRole, role));
        List<Long> pids = rps.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        if (pids.isEmpty()) return List.of();
        return listByIds(pids);
    }
    @Override
    @Transactional
    public void assignPermissions(String role, List<Long> permissionIds) {
        rolePermissionMapper.delete(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRole, role));
        for (Long pid : permissionIds) {
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermissionId(pid);
            rolePermissionMapper.insert(rp);
        }
    }
}
