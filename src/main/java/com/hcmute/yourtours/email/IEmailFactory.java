package com.hcmute.yourtours.email;

public interface IEmailFactory {
    void sendSimpleMessage(String to, String subject, String text);

    String getEmailActiveEmailContent(String userName, String expired, String otp);
}
