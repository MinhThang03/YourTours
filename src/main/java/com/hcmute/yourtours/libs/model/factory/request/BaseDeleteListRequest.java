package com.hcmute.yourtours.libs.model.factory.request;

import lombok.Data;

import java.util.List;

@Data
public class BaseDeleteListRequest<I> {
    List<I> ids;
}
