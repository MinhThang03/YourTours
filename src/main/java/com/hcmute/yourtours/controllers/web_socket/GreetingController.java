package com.hcmute.yourtours.controllers.web_socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@Slf4j
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greet(HelloMessage message) {
        log.info("Nhan tin tu socket: {}", message);

        return new Greeting("Hello, " +
                HtmlUtils.htmlEscape(message.getName()));
    }
}
