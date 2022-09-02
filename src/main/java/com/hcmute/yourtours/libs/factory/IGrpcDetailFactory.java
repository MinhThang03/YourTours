package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.model.BaseData;

public interface IGrpcDetailFactory<GRPC_DT, ID, IF extends BaseData<ID>, DT extends IF> extends IDataFactory<ID, IF, DT> {
    GRPC_DT convertDetailToGrpc(DT detail);

    DT convertGrpcToDetail(GRPC_DT detail);
}
