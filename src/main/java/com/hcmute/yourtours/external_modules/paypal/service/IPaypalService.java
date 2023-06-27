package com.hcmute.yourtours.external_modules.paypal.service;

import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.paypal.api.payments.Payment;

public interface IPaypalService {

    Payment createYourTourPayment(Double total) throws InvalidException;


    Payment createPayment(Double total, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws InvalidException;

    Payment executePayment(String paymentId, String payerId) throws InvalidException;
}
