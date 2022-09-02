package com.hcmute.yourtours.libs.model.factory.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BasePagingResponse<T> implements Serializable {
    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElements;

    public BasePagingResponse(List<T> content, int pageNumber, int pageSize, long totalElements) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
    }
}
