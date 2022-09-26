package com.hcmute.yourtours.libs.logging;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hcmute.yourtours.libs.client.ClientContext;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestClientLog {
    private final String url;
    private final HttpMethod method;
    private final HttpStatus status;
    private final Object body;
    private final Object response;

    private final Object params;

    public RestClientLog(String url, HttpMethod method, HttpStatus status, Object params, Object body, Object response) {
        this.url = url;
        this.method = method;
        this.status = status;
        this.body = body;
        this.params = params;
        this.response = response;
    }

    public RestClientLog(ClientContext context, Object body, ResponseEntity<?> response) {
        this(
                context.getUrl(),
                context.getMethod(),
                response != null ? response.getStatusCode() : null,
                null,
                body,
                response != null ? response.getBody() : null
        );
    }

    public RestClientLog(ClientContext context, Object params, Object body, ResponseEntity<?> response) {
        this(
                context.getUrl(),
                context.getMethod(),
                response != null ? response.getStatusCode() : null,
                params,
                body,
                response != null ? response.getBody() : null
        );
    }
}
