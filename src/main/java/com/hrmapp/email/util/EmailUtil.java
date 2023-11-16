package com.hrmapp.email.util;

import com.hrmapp.email.entity.Email;
import com.hrmapp.email.service.EmailService;
import jakarta.mail.MessagingException;
import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailUtil {
    private final EmailService emailService;
    private static final Logger log = LoggerFactory.getLogger(SLF4JLogger.class);

    public EmailUtil(EmailService emailService) {
        this.emailService = emailService;
    }

    public void sendEmail(String recipient, String subject, String template, Map<String, Object> properties){
        var email = Email.builder()
                .from("HRM APP")
                .to(recipient)
                .subject(subject)
                .template(template)
                .properties(properties)
                .build();
        try {
            emailService.sendHtmlEmail(email);
        }
        catch (MessagingException exception){
            log.error("Could not send password reset email because: {}", exception.getMessage());
        }
    }
}
