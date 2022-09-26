package com.hcmute.yourtours.libs.client;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

@SuperBuilder(toBuilder = true)
@Data
public class DefaultClientContext implements ClientContext {
    public static DefaultClientContext JSON;
    public static DefaultClientContext MULTIPART;

    static {
        HttpHeaders multipartHeader = new HttpHeaders();
        multipartHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
        MULTIPART = new DefaultClientContext(null, null, multipartHeader);

        HttpHeaders jsonHeader = new HttpHeaders();
        jsonHeader.setContentType(MediaType.APPLICATION_JSON);
        JSON = new DefaultClientContext(null, null, jsonHeader);
    }

    private String url;
    private HttpMethod httpMethod;
    private HttpHeaders headers;
    private boolean logging;

    public DefaultClientContext(String url, HttpMethod httpMethod, HttpHeaders headers) {
        this(url, httpMethod, headers, false);
    }

    public DefaultClientContext(String url, HttpMethod httpMethod, HttpHeaders headers, boolean logging) {
        this.url = url;
        this.httpMethod = httpMethod;
        this.headers = headers;
        this.logging = logging;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public HttpMethod getMethod() {
        return httpMethod;
    }

    @Override
    public HttpHeaders getHttpHeaders() {
        return headers;
    }

    @Override
    public boolean isLogging() {
        return logging;
    }
}
