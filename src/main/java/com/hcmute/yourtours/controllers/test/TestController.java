package com.hcmute.yourtours.controllers.test;

import com.hcmute.yourtours.libs.factory.IResponseFactory;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test/")
@Tag(name = "TEST API: TEST")
@Transactional
public class TestController {

    private final IResponseFactory iResponseFactory;

    public TestController(IResponseFactory iResponseFactory) {
        this.iResponseFactory = iResponseFactory;
    }

    @GetMapping("/admin")
    @Operation(summary = "admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> admin() {
        return iResponseFactory.success("admin");
    }


    @GetMapping("/user")
    @Operation(summary = "user")
    @PreAuthorize("hasRole('user')")
    public ResponseEntity<BaseResponse<String>> user() {
        return iResponseFactory.success("user");
    }

}
