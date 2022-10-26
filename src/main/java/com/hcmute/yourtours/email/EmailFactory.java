package com.hcmute.yourtours.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailFactory implements IEmailFactory {

    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    public EmailFactory(JavaMailSender emailSender, MailProperties mailProperties) {
        this.emailSender = emailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailProperties.getUsername());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        } catch (Exception ignore) {
            log.error("ERROR SEND EMAIL: {}  -- {}", ignore.getClass(), ignore.getMessage());
        }

    }
}
