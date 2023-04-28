package com.hcmute.yourtours.external_modules.email;

import com.hcmute.yourtours.models.booking.BookHomeDetail;
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
            log.error("ERROR SEND SIMPLE EMAIL: {}  -- {}", ignore.getClass(), ignore.getMessage());
        }

    }

    @Override
    public String getEmailActiveEmailContent(String userName, String expired, String otp) {
        return getEmailOtp(userName, expired, otp, "Email_Active_Email.ftl");
    }

    @Override
    public String getEmailResendOtpContent(String userName, String expired, String otp) {
        return getEmailOtp(userName, expired, otp, "Email_Resend_Email.ftl");
    }

    @Override
    public String getEmailForgotPasswordContent(String userName, String expired, String otp) {
        return getEmailOtp(userName, expired, otp, "Email_Forgot_Password.ftl");
    }

    @Override
    public String getEmailSuccessBooking(BookHomeDetail detail) {
        try {
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("homeName", detail.getHomeName());
            model.put("ownerName", detail.getOwner());
            model.put("dateStart", detail.getDateStart().toString());
            model.put("dateEnd", detail.getDateEnd().toString());
            model.put("baseCost", String.valueOf(detail.getBaseCost().longValue()));
            model.put("surchargeCost", detail.getSurchargeCost() == null ? "0" : String.valueOf(detail.getSurchargeCost().longValue()));
            model.put("discount", detail.getPercent() == null ? "0" : detail.getPercent().toString());
            model.put("totalCost", String.valueOf(detail.getTotalCost().longValue()));
            model.put("moneyPay", String.valueOf(detail.getMoneyPayed().longValue()));
            model.put("billId", detail.getId().toString());
            model.put("createdDate", detail.getCreatedDate());
            model.put("userName", detail.getCustomerName());
            freeMarkerConfiguration.getTemplate("Email_Booking_Success.ftl").process(model, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (Exception ignore) {
            ignore.printStackTrace();
            log.error("ERROR SEND EMAIL SUCCESS BOOKING: {}  -- {}", ignore.getClass(), ignore.getMessage());
            return null;
        }
    }

    @Override
    public String getEmailCancelBooking(BookHomeDetail detail) {
        try {
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("homeName", detail.getHomeName());
            model.put("ownerName", detail.getOwner());
            model.put("dateStart", detail.getDateStart().toString());
            model.put("dateEnd", detail.getDateEnd().toString());
            model.put("baseCost", String.valueOf(detail.getBaseCost().longValue()));
            model.put("totalCost", String.valueOf(detail.getTotalCost().longValue()));
            model.put("moneyPay", String.valueOf(detail.getMoneyPayed().longValue()));
            model.put("status", detail.getStatus().getDescription());
            model.put("billId", detail.getId().toString());
            model.put("cancelDate", detail.getLastModifiedDate());
            model.put("userName", detail.getCustomerName());
            freeMarkerConfiguration.getTemplate("Email_Booking_Cancel.ftl").process(model, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (Exception ignore) {
            ignore.printStackTrace();
            log.error("ERROR SEND EMAIL CANCEL BOOKING: {}  -- {}", ignore.getClass(), ignore.getMessage());
            return null;
        }
    }


    private String getEmailOtp(String userName, String expired, String otp, String fileNameTemplate) {
        try {
            StringWriter stringWriter = new StringWriter();
            Map<String, Object> model = new HashMap<>();
            model.put("name", userName);
            model.put("expired", expired);
            model.put("otp", otp);
            freeMarkerConfiguration.getTemplate(fileNameTemplate).process(model, stringWriter);
            return stringWriter.getBuffer().toString();
        } catch (Exception ignore) {
            ignore.printStackTrace();
            log.error("ERROR SEND EMAIL OTP: {}  -- {}", ignore.getClass(), ignore.getMessage());
            return null;
        }
    }


}
