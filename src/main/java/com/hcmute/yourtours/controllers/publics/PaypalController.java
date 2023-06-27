package com.hcmute.yourtours.controllers.publics;

import com.hcmute.yourtours.controllers.publics.interfaces.IPayPalController;
import com.hcmute.yourtours.external_modules.paypal.service.IPaypalService;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/public/paypal")
@Tag(name = "PUBLIC API: PAYPAL", description = "API public test thanh toán ")
@Transactional
@Slf4j
public class PaypalController implements IPayPalController {

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";
    private final IPaypalService iPaypalService;
    public PaypalController(IPaypalService iPaypalService) {
        this.iPaypalService = iPaypalService;
    }

    @Override
    public String payment() {
        try {
            Payment payment = iPaypalService.createYourTourPayment(1.0);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return "redirect:" + link.getHref();
                }
            }
            return "redirect:/";
        } catch (InvalidException e) {
            throw new RestException(e);
        }
    }


    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = iPaypalService.executePayment(paymentId, payerId);
            log.info("PAYPAL RESPONSE: {}", payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
            return "redirect:/";
        } catch (InvalidException e) {
            throw new RestException(e);
        }

    }
}
