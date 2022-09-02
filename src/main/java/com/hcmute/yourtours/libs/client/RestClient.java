package com.hcmute.yourtours.libs.client;


import com.hcmute.yourtours.libs.exceptions.InvalidException;

public interface RestClient {
    <T, C extends ClientContext> T callAPI(
            Object request,
            Class<T> responseClassType,
            C context,
            IRestClientChecker<T> responseDataChecker
    ) throws InvalidException;

    <T, C extends ClientContext> T callAPIBaseResponse(
            Object request,
            Class<T> responseClassType,
            C context,
            IRestClientChecker<T> responseDataChecker
    ) throws InvalidException;


}
