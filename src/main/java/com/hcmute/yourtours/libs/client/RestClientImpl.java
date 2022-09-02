package com.hcmute.yourtours.libs.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.DeserializationProblemHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.hcmute.yourtours.libs.exceptions.ErrorCode;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.logging.LogContext;
import com.hcmute.yourtours.libs.logging.LogType;
import com.hcmute.yourtours.libs.logging.RestClientLog;
import com.hcmute.yourtours.libs.model.factory.response.BaseResponse;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.IdentityHashMap;
import java.util.Map;

@Slf4j
public class RestClientImpl implements RestClient {
    private final RestTemplate restTemplate;
    private ObjectMapper mapper;

    public RestClientImpl() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        this.restTemplate = createSslTrustedRestTemplate();
    }

    @Override
    public <T, C extends ClientContext> T callAPI(Object request, Class<T> responseClassType, C context, IRestClientChecker<T> responseChecker) throws InvalidException {
        ResponseEntity<T> response = null;
        try {

            HttpEntity<Object> httpEntity = new HttpEntity<>(request, context.getHttpHeaders());
            response = restTemplate.exchange(
                    context.getUrl(),
                    context.getMethod(),
                    httpEntity,
                    responseClassType,
                    uriVariables(request)
            );

            if (!context.responseChecker().isSuccess(response)) {
                throw new InvalidException(ErrorCode.INTERNAL_BAD_REQUEST);
            }

            if (responseChecker != null && !responseChecker.isSuccess(response.getBody())) {
                throw new InvalidException(ErrorCode.INTERNAL_BAD_REQUEST);
            }
            return mapper().convertValue(response.getBody(), responseClassType);
        } catch (RestClientException e) {
            throw new InvalidException(ErrorCode.SERVER_ERROR);
        } finally {
            try {
                if (context.isLogging()) {
                    RestClientLog restClientLog = new RestClientLog(
                            context,
                            request,
                            response
                    );
                    LogContext.push(LogType.CALL_API, restClientLog);
                }
            } catch (Exception e) {
                log.warn("RestClient logging error in {}: {}", e.getClass().getSimpleName(), e.getMessage());
            }
        }
    }


    @Override
    public <T, C extends ClientContext> T callAPIBaseResponse(Object request, Class<T> responseClassType, C context, IRestClientChecker<T> responseDataChecker) throws InvalidException {
        BaseResponse<T> response = callAPI(
                request,
                BaseResponse.class,
                context,
                (responseData) -> responseData != null && responseData.isSuccess()
        );
        if (responseDataChecker != null && responseDataChecker.isSuccess(response.getData())) {
            throw new InvalidException(ErrorCode.INTERNAL_BAD_REQUEST);
        }
        return response.getData();
    }

    protected Object uriVariables(Object request) {
        Map<String, Object> uriVariables = new IdentityHashMap<>();
        if (request != null) {
            uriVariables = mapper().convertValue(request, Map.class);
        }
        return uriVariables;
    }

    protected RestTemplate createSslTrustedRestTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return new RestTemplate(requestFactory);
    }

    protected ObjectMapper mapper() {
        if (this.mapper != null) {
            return mapper;
        }
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .addHandler(
                        new DeserializationProblemHandler() {
                            @Override
                            public Object handleWeirdStringValue(DeserializationContext ctxt, Class<?> targetType, String valueToConvert, String failureMsg) throws IOException {
                                return null;
                            }

                            @Override
                            public Object handleWeirdNumberValue(DeserializationContext ctxt, Class<?> targetType, Number valueToConvert, String failureMsg) throws IOException {
                                return null;
                            }
                        }
                );
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
