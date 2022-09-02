package com.hcmute.yourtours.libs.client;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ClientContext extends Cloneable {
    String getUrl();

    HttpMethod getMethod();

    HttpHeaders getHttpHeaders();

    boolean isLogging();

    default IRestClientChecker<ResponseEntity<?>> responseChecker() {
        return (response) -> response != null && response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null;
    }

}
