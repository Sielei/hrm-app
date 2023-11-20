package com.hrmapp.user.application.controller;

import com.hrmapp.common.application.dto.GenericResponse;
import com.hrmapp.user.application.dto.PageQuery;
import com.hrmapp.user.application.dto.PagedResult;
import com.hrmapp.user.application.dto.UserRole;
import com.hrmapp.user.application.dto.request.CreateUserRequest;
import com.hrmapp.user.application.dto.request.DeactivateUserRequest;
import com.hrmapp.user.application.dto.request.UpdatePasswordRequest;
import com.hrmapp.user.application.dto.request.UpdateUserRequest;
import com.hrmapp.user.application.dto.response.CreateUserResponse;
import com.hrmapp.user.application.port.input.UserApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER_FULL_ACCESS', 'CREATE_USERS')")
    public ResponseEntity<CreateUserResponse> createUser(@RequestAttribute("userId") UUID userId,
                                                         @RequestBody @Valid CreateUserRequest createUserRequest){
        var createdUser = userApplicationService.handleCreateUserCommand(createUserRequest, userId);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('USER_FULL_ACCESS', 'EDIT_ANY_USER')")
    public ResponseEntity<CreateUserResponse> updateUserDetails(@PathVariable("userId") UUID userId, @RequestBody UpdateUserRequest updateUserRequest){
        var updatedUser = userApplicationService.handleUpdateUserRequest(userId, updateUserRequest);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @PostMapping("/{userId}/assign-roles")
    public ResponseEntity<CreateUserResponse> assignRoles(@PathVariable("userId") UUID userId, @RequestBody List<UserRole> userRoles){
        return new ResponseEntity<>(userApplicationService.handleAssignRoles(userId, userRoles), HttpStatus.OK);
    }

    @PostMapping("/{userId}/remove-roles")
    public ResponseEntity<CreateUserResponse> removeRoles(@PathVariable("userId") UUID userId, @RequestBody List<UserRole> userRoles){
        return new ResponseEntity<>(userApplicationService.handleRemoveRoles(userId, userRoles), HttpStatus.OK);
    }
    @GetMapping
    public PagedResult<CreateUserResponse> getUsers(@RequestParam(name = "page", defaultValue = "1") Integer pageNo,
                                                    @RequestParam(name = "size", defaultValue = "10") Integer pageSize){
        var pageQuery = PageQuery.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
        return userApplicationService.findAllUsers(pageQuery);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CreateUserResponse> getUserById(@PathVariable("userId") UUID userId){
        return new ResponseEntity<>(userApplicationService.findUserByUserId(userId), HttpStatus.OK);
    }

    @PostMapping("/update-password")
    public ResponseEntity<GenericResponse> updatePassword(@RequestAttribute("userId") UUID userId,
                                                          @RequestBody UpdatePasswordRequest updatePasswordRequest){
        userApplicationService.handleUpdatePasswordRequest(userId, updatePasswordRequest);
        return new ResponseEntity<>(GenericResponse.builder()
                .success(true)
                .message("Password updated successfully!")
                .build(), HttpStatus.OK);
    }

    @PostMapping("/deactivate")
    public ResponseEntity<CreateUserResponse> deactivateUser(@RequestAttribute("userId") UUID deactivatedBy,
                                                  @RequestBody DeactivateUserRequest deactivateUserRequest){
        var deactivatedUser = userApplicationService.handleDeactivateUserRequest(deactivatedBy, deactivateUserRequest);
        return new ResponseEntity<>(deactivatedUser, HttpStatus.OK);
    }
}
