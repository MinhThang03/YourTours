package com.hcmute.yourtours.libs.factory;

import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

import java.util.List;

public interface IGrpcFactoryManager {

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> GRPC_DT create(GRPC_DT detail, Class<GRPC_DT> responseClass);

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> GRPC_DT update(I id, GRPC_DT detail, Class<GRPC_DT> responseClass);

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> boolean delete(I id, F data, Class<GRPC_DT> responseClass);

    <GRPC_IF, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> List<GRPC_IF> getList(F filter, Class<GRPC_IF> responseClass);

    <GRPC_IF, I, IF extends BaseData<I>, DT extends IF> List<GRPC_IF> getList(Class<GRPC_IF> responseClass);

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> GRPC_DT getDetail(I id, F filter, Class<GRPC_DT> responseClass);

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> boolean existByFilter(I id, F filter, Class<GRPC_DT> responseClass);

    <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> boolean existByDetail(GRPC_DT detail, Class<GRPC_DT> responseClass);
}
