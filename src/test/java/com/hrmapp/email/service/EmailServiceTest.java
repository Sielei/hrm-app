package com.hrmapp.email.service;


import com.hrmapp.email.entity.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;
    private EmailService emailService;
    @BeforeEach
    void init(){
        var springTemplateEngine = new SpringTemplateEngine();
        var javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setPort(587);

        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        emailService = new EmailService(javaMailSender, springTemplateEngine);
    }
    @Test
    public void sendHtmlMessageTest() {
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", "Mkali Wao");
        properties.put("emailDate", LocalDate.now().toString());
        var email = Email.builder()
                .from("HRM-APP")
                .to("anonh3ck@yahoo.com")
                .subject("Test email from HRM APP")
                .template("test-email.html")
                .properties(properties)
                .build();
        assertThatNoException().isThrownBy(()-> emailService.sendHtmlEmail(email));
    }
}