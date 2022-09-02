package com.hcmute.yourtours.libs.client;

public interface IRestClientChecker<T> {
    boolean isSuccess(T data);
}
