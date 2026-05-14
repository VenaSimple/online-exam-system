package com.exam.controller;

import com.exam.common.PageResult;
import com.exam.common.Result;
import com.exam.dto.OrganizationDTO;
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
    public Result<List<OrganizationDTO>> tree() {
        return Result.success(orgService.getOrgTree());
    }

    @GetMapping("/list")
    public Result<PageResult<OrganizationDTO>> list(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer type) {
        return Result.success(orgService.listOrganizations(pageNum, pageSize, keyword, type));
    }

    @GetMapping("/{id}")
    public Result<OrganizationDTO> getById(@PathVariable Long id) {
        return Result.success(orgService.getOrganizationById(id));
    }

    @PostMapping
    public Result<Void> create(@RequestBody OrganizationDTO dto) {
        orgService.createOrg(dto);
        return Result.success();
    }

    @PutMapping
    public Result<Void> update(@RequestBody OrganizationDTO dto) {
        orgService.updateOrg(dto);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        orgService.deleteOrg(id);
        return Result.success();
    }
}
