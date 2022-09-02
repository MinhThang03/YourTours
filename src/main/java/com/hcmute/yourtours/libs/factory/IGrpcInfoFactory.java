package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.model.BaseData;

public interface IGrpcInfoFactory<GRPC_IF, ID, IF extends BaseData<ID>, DT extends IF> extends IDataFactory<ID, IF, DT> {
    GRPC_IF convertInfoToGrpc(IF info);

    IF convertGrpcToInfo(GRPC_IF info);
}
