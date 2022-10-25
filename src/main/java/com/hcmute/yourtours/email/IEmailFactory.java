package com.hcmute.yourtours.email;

public interface IEmailFactory {
    void sendSimpleMessage(String to, String subject, String text);
}
