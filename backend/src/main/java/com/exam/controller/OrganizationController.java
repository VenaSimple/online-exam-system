package com.exam.controller;

import com.exam.common.Result;
import com.exam.entity.Organization;
import com.exam.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/org")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService orgService;

    @GetMapping("/tree")
    public Result<List<Organization>> tree() {
        return Result.success(orgService.getOrgTree());
    }

    @PostMapping
    public Result<Void> create(@RequestBody Organization org) {
        orgService.createOrg(org);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody Organization org) {
        orgService.updateOrg(org);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        orgService.deleteOrg(id);
        return Result.success();
    }
}
