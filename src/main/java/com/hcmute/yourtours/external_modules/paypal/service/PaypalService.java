package com.hcmute.yourtours.external_modules.paypal.service;

import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.hcmute.yourtours.controllers.publics.PaypalController.CANCEL_URL;
import static com.hcmute.yourtours.controllers.publics.PaypalController.SUCCESS_URL;

@Service
public class PaypalService implements IPaypalService {

    private final APIContext apiContext;

    public PaypalService(APIContext apiContext) {
        this.apiContext = apiContext;
    }


    @Override
    public Payment createYourTourPayment(Double total) throws InvalidException {
        return createPayment(
                total,
                "USD",
                "paypal",
                "sale",
                "testing",
                "http://localhost:8082/api/v1/public/paypal/".concat(CANCEL_URL),
                "http://localhost:8082/api/v1/public/paypal/".concat(SUCCESS_URL)
//                "https://your-tours-client.vercel.app/",
//                "https://your-tours-client.vercel.app/list-room"
        );
    }

    @Override
    public Payment createPayment(Double total, String currency, String method, String intent, String description, String cancelUrl, String successUrl) throws InvalidException {
        try {
            Amount amount = new Amount();
            amount.setCurrency(currency);
            total = BigDecimal.valueOf(total).setScale(2, RoundingMode.HALF_UP).doubleValue();
            amount.setTotal(String.format("%.2f", total));

            Transaction transaction = new Transaction();
            transaction.setDescription(description);
            transaction.setAmount(amount);

            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);

            Payer payer = new Payer();
            payer.setPaymentMethod(method);

            Payment payment = new Payment();
            payment.setIntent(intent);
            payment.setPayer(payer);
            payment.setTransactions(transactions);
            RedirectUrls redirectUrls = new RedirectUrls();
            redirectUrls.setCancelUrl(cancelUrl);
            redirectUrls.setReturnUrl(successUrl);
            payment.setRedirectUrls(redirectUrls);

            return payment.create(apiContext);
        } catch (PayPalRESTException exception) {
            throw new InvalidException(ErrorCode.BAD_REQUEST);
        }


    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws InvalidException {
        try {
            Payment payment = new Payment();
            payment.setId(paymentId);
            PaymentExecution paymentExecute = new PaymentExecution();
            paymentExecute.setPayerId(payerId);
            return payment.execute(apiContext, paymentExecute);
        } catch (PayPalRESTException exception) {
            throw new InvalidException(ErrorCode.BAD_REQUEST);
        }

    }
}
