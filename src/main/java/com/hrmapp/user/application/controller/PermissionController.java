package com.hrmapp.user.application.controller;

import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.PermissionDto;
import com.hrmapp.user.application.port.input.UserApplicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final UserApplicationService userApplicationService;

    public PermissionController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @GetMapping
    PagedResult<PermissionDto> findAllPermissions(@RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                                                  @RequestParam(name = "size", defaultValue = "10") Integer pageSize){
        var pageQuery = PageQuery.builder().pageNo(pageNo).pageSize(pageSize).build();
        return userApplicationService.findAllPermissions(pageQuery);
    }
}
