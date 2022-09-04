package com.hcmute.yourtours.controllers.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/random")
    public String randomStuff() {
        return "JWT Hợp lệ mới có thể thấy được message này";
    }
}
