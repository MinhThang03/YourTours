package com.hcmute.yourtours.controllers.publics.interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface IPayPalController {

    @PostMapping("/pay")
    String payment();

    @GetMapping(value = "/cancel")
    String cancelPay();

    @GetMapping(value = "/success")
    String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId);
}
