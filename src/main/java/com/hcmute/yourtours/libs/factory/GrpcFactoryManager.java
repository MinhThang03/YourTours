package com.hcmute.yourtours.libs.factory;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;
import com.hcmute.yourtours.libs.exceptions.InvalidException;
import com.hcmute.yourtours.libs.exceptions.RestException;
import com.hcmute.yourtours.libs.model.BaseData;
import com.hcmute.yourtours.libs.model.filter.BaseFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Transactional
public class GrpcFactoryManager implements IGrpcFactoryManager {
    private static final Map<Class<?>, IGrpcDetailFactory<?, ?, ?, ?>> detailFactoryRegistries = new HashMap<>();
    private static final Map<Class<?>, IGrpcInfoFactory<?, ?, ?, ?>> infoFactoryRegistries = new HashMap<>();
    private final ApplicationContext applicationContext;

    public GrpcFactoryManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.initFactory();
    }

    protected void initFactory() {
        for (Map.Entry<String, IGrpcDetailFactory> factory : this.applicationContext.getBeansOfType(IGrpcDetailFactory.class).entrySet()) {
            Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(factory.getValue().getClass(), IGrpcDetailFactory.class);
            assert generics != null;
            Class<?> responseClass = generics[0];
            if (detailFactoryRegistries.containsKey(responseClass)) {
                throw new RuntimeException(String.format("Duplicate IGrpcDetailFactory beans of type %s", responseClass.getSimpleName()));
            }
            detailFactoryRegistries.put(responseClass, factory.getValue());
        }
        for (Map.Entry<String, IGrpcInfoFactory> factory : this.applicationContext.getBeansOfType(IGrpcInfoFactory.class).entrySet()) {
            Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(factory.getValue().getClass(), IGrpcInfoFactory.class);
            assert generics != null;
            Class<?> responseClass = generics[0];
            if (infoFactoryRegistries.containsKey(responseClass)) {
                throw new RuntimeException(String.format("Duplicate IGrpcInfoFactory beans of type %s", responseClass.getSimpleName()));
            }
            infoFactoryRegistries.put(responseClass, factory.getValue());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> GRPC_DT create(GRPC_DT detail, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);
        try {
            return factory.convertDetailToGrpc(
                    factory.createModel(
                            factory.convertGrpcToDetail(detail)
                    )
            );
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> GRPC_DT update(I id, GRPC_DT detail, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);
        try {
            return factory.convertDetailToGrpc(
                    factory.updateModel(
                            id,
                            factory.convertGrpcToDetail(detail)
                    )
            );
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> boolean delete(I id, F data, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);


        try {
            return factory.deleteModel(
                    id,
                    data
            );
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }

    }

    @Override
    public <GRPC_IF, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> List<GRPC_IF> getList(F filter, Class<GRPC_IF> responseClass) {
        IGrpcInfoFactory<GRPC_IF, I, IF, DT> factory = (IGrpcInfoFactory<GRPC_IF, I, IF, DT>) infoFactoryRegistries.get(responseClass);
        try {
            return factory.getInfoList(
                    filter
            ).stream().map(
                    factory::convertInfoToGrpc
            ).collect(Collectors.toList());
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_IF, I, IF extends BaseData<I>, DT extends IF> List<GRPC_IF> getList(Class<GRPC_IF> responseClass) {
        IGrpcInfoFactory<GRPC_IF, I, IF, DT> factory = (IGrpcInfoFactory<GRPC_IF, I, IF, DT>) infoFactoryRegistries.get(responseClass);
        try {
            return factory.getInfoList(
            ).stream().map(
                    factory::convertInfoToGrpc
            ).collect(Collectors.toList());
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> GRPC_DT getDetail(I id, F filter, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);
        try {
            DT response = factory.getDetailModel(id, filter);
            return factory.convertDetailToGrpc(response);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF, F extends BaseFilter> boolean existByFilter(I id, F filter, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);
        try {
            return factory.existByFilter(id, filter);
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }

    @Override
    public <GRPC_DT, I, IF extends BaseData<I>, DT extends IF> boolean existByDetail(GRPC_DT detail, Class<GRPC_DT> responseClass) {
        IGrpcDetailFactory<GRPC_DT, I, IF, DT> factory = (IGrpcDetailFactory<GRPC_DT, I, IF, DT>) detailFactoryRegistries.get(responseClass);
        try {
            return factory.existByDetail(factory.convertGrpcToDetail(detail));
        } catch (InvalidException e) {
            throw new RestException(e.getErrorCode());
        }
    }
}
