package com.hcmute.yourtours.email;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class EmailFactory implements IEmailFactory {

    final Configuration freeMarkerConfiguration;
    private final JavaMailSender emailSender;
    private final MailProperties mailProperties;

    public EmailFactory
            (
                    JavaMailSender emailSender,
                    MailProperties mailProperties,
                    Configuration freeMarkerConfiguration
            ) {
        this.emailSender = emailSender;
        this.mailProperties = mailProperties;
        this.freeMarkerConfiguration = freeMarkerConfiguration;
    }


    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        if (text == null) {
            return;
        }
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setSubject(subject);
            helper.setFrom(mailProperties.getUsername());
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(mimeMessage);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            log.error("ERROR SEND EMAIL: {}  -- {}", ignore.getClass(), ignore.getMessage());
        }

    }

    @Override
    public String getEmailActiveEmailContent(String userName, String expired, String otp) {
        try {
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("name", userName);
            model.put("expired", expired);
            model.put("otp", otp);
            freeMarkerConfiguration.getTemplate("Email_Active_Email.ftl").process(model, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (Exception ignore) {
            ignore.printStackTrace();
            log.error("ERROR SEND EMAIL: {}  -- {}", ignore.getClass(), ignore.getMessage());
            return null;
        }
    }
}
