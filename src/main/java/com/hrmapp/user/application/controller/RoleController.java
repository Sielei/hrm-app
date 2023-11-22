package com.hrmapp.user.application.controller;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.RoleDto;
import com.hrmapp.user.application.dto.RolePermission;
import com.hrmapp.user.application.dto.request.CreateRoleRequest;
import com.hrmapp.user.application.dto.request.UpdateRoleRequest;
import com.hrmapp.user.application.port.input.UserApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final UserApplicationService userApplicationService;

    public RoleController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestAttribute("userId") UUID userId,
                                              @RequestBody CreateRoleRequest createRoleRequest){
        var createdRole = userApplicationService.handleCreateRoleRequest(createRoleRequest, userId);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdRole.id())
                .toUri();
        return ResponseEntity.created(location).body(createdRole);
    }

    @GetMapping("/{roleId}")
    public RoleDto findRoleById(@PathVariable("roleId") UUID roleId){
        return userApplicationService.findRoleById(roleId);
    }

    @PostMapping("/{roleId}/add-permissions")
    public RoleDto addRolePermissions(@PathVariable("roleId") UUID roleId, @RequestBody Set<RolePermission> rolePermissions){
        return userApplicationService.handleAddPermissions(roleId, rolePermissions);
    }

    @PostMapping("/{roleId}/remove-permissions")
    public RoleDto removeRolePermissions(@PathVariable("roleId") UUID roleId, @RequestBody Set<RolePermission> rolePermissions){
        return userApplicationService.handleRemoveRolePermissions(roleId, rolePermissions);
    }

    @PutMapping("/{roleId}")
    public RoleDto updateRole(@PathVariable("roleId") UUID roleId, @RequestBody UpdateRoleRequest updateRoleRequest){
        return userApplicationService.handleUpdateRoleRequest(roleId, updateRoleRequest);
    }

    @GetMapping
    public PagedResult<RoleDto> getRoles(@RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                                         @RequestParam(name = "size", defaultValue = "10") Integer pageSize){
        var pageQuery = PageQuery.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
        return userApplicationService.findAllRoles(pageQuery);
    }

    @DeleteMapping("/{roleId}")
    public GenericResponse deleteRole(@PathVariable("roleId") UUID roleId){
        userApplicationService.handleDeleteRole(roleId);
        return GenericResponse.builder()
                .success(true)
                .message("The role has been deleted successfully!")
                .build();
    }
}
