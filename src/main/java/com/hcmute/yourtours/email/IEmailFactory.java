package com.hcmute.yourtours.email;

import com.hcmute.yourtours.models.booking.BookHomeDetail;

public interface IEmailFactory {
    void sendSimpleMessage(String to, String subject, String text);

    String getEmailActiveEmailContent(String userName, String expired, String otp);

    String getEmailResendOtpContent(String userName, String expired, String otp);

    String getEmailForgotPasswordContent(String userName, String expired, String otp);

    String getEmailSuccessBooking(BookHomeDetail detail);
}
