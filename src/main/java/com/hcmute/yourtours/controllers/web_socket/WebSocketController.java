package com.hcmute.yourtours.controllers.web_socket;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/socket/")
@RequiredArgsConstructor
@Transactional
@Tag(name = "API: SOCKET")
@Slf4j
public class WebSocketController {


}
