package com.exam.controller;
import com.exam.common.Result;
import com.exam.entity.Permission;
import com.exam.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping("/tree")
    public Result<List<Permission>> tree() { return Result.success(permissionService.getPermissionTree()); }
    @GetMapping("/role/{role}")
    public Result<List<Permission>> byRole(@PathVariable String role) { return Result.success(permissionService.getPermissionsByRole(role)); }
    @PostMapping("/assign")
    public Result<Void> assign(@RequestParam String role, @RequestBody List<Long> permissionIds) { permissionService.assignPermissions(role, permissionIds); return Result.success(); }
}
