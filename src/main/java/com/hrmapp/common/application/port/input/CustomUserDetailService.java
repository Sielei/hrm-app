package com.hrmapp.common.application.port.input;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.user.application.port.input.UserApplicationService;
import com.hrmapp.user.domain.entity.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserApplicationService userApplicationService;

    public CustomUserDetailService(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDto = userApplicationService.findUserByUsername(username);
        return new User(userDto.username(), userDto.password(), getAuthorities(userDto));
    }
    private Collection<? extends GrantedAuthority> getAuthorities(UserDto userDto) {
        var roles = userDto.roles();
        var permissions = new HashSet<Permission>();
        var grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (var role : roles){
            permissions.addAll(role.getPermissions());
        }
        for (var permission: permissions){
            grantedAuthorities.add(new SimpleGrantedAuthority(permission.getAuthority()));
        }
        return grantedAuthorities;
    }
}
