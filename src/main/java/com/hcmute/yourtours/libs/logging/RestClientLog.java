package com.hcmute.yourtours.libs.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.hcmute.yourtours.libs.client.ClientContext;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestClientLog {
    private final String url;
    private final HttpMethod method;
    private final HttpStatus status;
    private final Object request;
    private final Object response;

    public RestClientLog(String url, HttpMethod method, HttpStatus status, Object request, Object response) {
        this.url = url;
        this.method = method;
        this.status = status;
        this.request = request;
        this.response = response;
    }

    public RestClientLog(ClientContext context, Object request, ResponseEntity<?> response) {
        this(
                context.getUrl(),
                context.getMethod(),
                response != null ? response.getStatusCode() : null,
                request, response != null ? response.getBody() : null
        );
    }
}
