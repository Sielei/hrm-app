package com.hrmapp.user.application.controller;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.PasswordPolicyDto;
import com.hrmapp.user.application.dto.request.CreatePasswordPolicyRequest;
import com.hrmapp.user.application.dto.request.CreatePasswordPolicyResponse;
import com.hrmapp.user.application.dto.request.UpdatePasswordPolicyRequest;
import com.hrmapp.user.application.port.input.UserApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/api/password-policies")
public class PasswordPolicyController {
    private final UserApplicationService userApplicationService;

    public PasswordPolicyController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    public ResponseEntity<CreatePasswordPolicyResponse> createPasswordPolicy(@RequestBody CreatePasswordPolicyRequest createPasswordPolicyRequest){
        var newPasswordPolicy = userApplicationService.handleCreatePasswordPolicy(createPasswordPolicyRequest);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newPasswordPolicy.id())
                .toUri();
        return ResponseEntity.created(location).body(newPasswordPolicy);
    }

    @GetMapping
    public PagedResult<PasswordPolicyDto> findAllPasswordPolicies(@RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                                                                  @RequestParam(name = "size", defaultValue = "10") Integer pageSize){
        var pageQuery = PageQuery.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
        return userApplicationService.findAllPasswordPolicies(pageQuery);
    }

    @GetMapping("/{passwordPolicyId}")
    public ResponseEntity<PasswordPolicyDto> getPasswordPolicyById(@PathVariable("passwordPolicyId") UUID passwordPolicyId){
        var passwordPolicyDTO = userApplicationService.findPasswordPolicyById(passwordPolicyId);
        return new ResponseEntity<>(passwordPolicyDTO, HttpStatus.OK);
    }

    @PutMapping("/{passwordPolicyId}")
    public PasswordPolicyDto updatePasswordPolicy(@PathVariable("passwordPolicyId") UUID passwordPolicyId,
                                                                @RequestBody UpdatePasswordPolicyRequest updatePasswordPolicyRequest){
        return userApplicationService.handleUpdatePasswordPolicyRequest(passwordPolicyId, updatePasswordPolicyRequest);
    }

    @DeleteMapping("/{passwordPolicyId}")
    public GenericResponse deletePasswordPolicyById(@PathVariable("passwordPolicyId") UUID passwordPolicyId){
        userApplicationService.handleDeletePasswordPolicy(passwordPolicyId);
        return GenericResponse.builder()
                .success(true)
                .message("Password policy has been deleted successfully!")
                .build();
    }
}