package com.hrmapp.user.application.port.input.handler.command;

import com.hrmapp.common.application.dto.UserDto;
import com.hrmapp.email.util.EmailUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserEmailHandler {
    private final EmailUtil emailUtil;

    public UserEmailHandler(EmailUtil emailUtil) {
        this.emailUtil = emailUtil;
    }

    public void sendWelcomeEmail(UserDto userDto, String generatedPassword) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", userDto.username());
        properties.put("username", userDto.username());
        properties.put("password", generatedPassword);
        var subject = "Getting Started with HRM-APP";
        var template = "welcome-email.html";
        emailUtil.sendEmail(userDto.emailAddress(), subject, template, properties);
    }
}
