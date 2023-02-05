package com.hcmute.yourtours.utils.uuid_projection;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.jpa.projection.CollectionAwareProjectionFactory;
import org.springframework.data.projection.DefaultMethodInvokingMethodInterceptor;
import org.springframework.data.projection.MethodInterceptorFactory;
import org.springframework.data.projection.TargetAware;
import org.springframework.data.util.NullableWrapperConverters;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UUIDProjection extends CollectionAwareProjectionFactory {
    final static GenericConversionService CONVERSION_SERVICE = new DefaultConversionService();

    static {
        Jsr310Converters.getConvertersToRegister().forEach(CONVERSION_SERVICE::addConverter);
        NullableWrapperConverters.registerConvertersIn(CONVERSION_SERVICE);
        CONVERSION_SERVICE.removeConvertible(Object.class, Object.class);
        CONVERSION_SERVICE.addConverter(new UUIDConvert());
    }

    private final List<MethodInterceptorFactory> factories;
    private @Nullable
    ClassLoader classLoader;

    public UUIDProjection() {
        this.factories = new ArrayList<>();
        this.factories.add(MapAccessingMethodInterceptorFactory.INSTANCE);
        this.factories.add(PropertyAccessingMethodInvokerFactory.INSTANCE);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
        super.setBeanClassLoader(classLoader);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProjection(Class<T> projectionType, Object source) {

        Assert.notNull(projectionType, "Projection type must not be null");
        Assert.notNull(source, "Source must not be null");
        Assert.isTrue(projectionType.isInterface(), "Projection type must be an interface");

        if (projectionType.isInstance(source)) {
            return (T) source;
        }

        ProxyFactory factory = new ProxyFactory();
        factory.setTarget(source);
        factory.setOpaque(true);
        factory.setInterfaces(projectionType, TargetAware.class);

        factory.addAdvice(new DefaultMethodInvokingMethodInterceptor());
        factory.addAdvice(new TargetAwareMethodInterceptor(source.getClass()));
        factory.addAdvice(getMethodInterceptor(source, projectionType));

        return (T) factory.getProxy(classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader);
    }

    private MethodInterceptor getMethodInterceptor(Object source, Class<?> projectionType) {

        MethodInterceptor propertyInvocationInterceptor = getFactoryFor(source, projectionType)
                .createMethodInterceptor(source, projectionType);

        return new ProjectingMethodInterceptor(this,
                postProcessAccessorInterceptor(propertyInvocationInterceptor, source, projectionType), CONVERSION_SERVICE);
    }

    private MethodInterceptorFactory getFactoryFor(Object source, Class<?> projectionType) {

        for (MethodInterceptorFactory factory : factories) {
            if (factory.supports(source, projectionType)) {
                return factory;
            }
        }

        throw new IllegalStateException("No MethodInterceptorFactory found for type ".concat(source.getClass().getName()));
    }

    /**
     * {@link MethodInterceptorFactory} handling {@link Map}s as target objects.
     *
     * @author Oliver Gierke
     */
    private enum MapAccessingMethodInterceptorFactory implements MethodInterceptorFactory {

        INSTANCE;

        /*
         * (non-Javadoc)
         * @see org.springframework.data.projection.MethodInterceptorFactory#createMethodInterceptor(java.lang.Object, java.lang.Class)
         */
        @Override
        @SuppressWarnings("unchecked")
        public MethodInterceptor createMethodInterceptor(Object source, Class<?> targetType) {
            return new MapAccessingMethodInterceptor((Map<String, Object>) source);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.projection.MethodInterceptorFactory#supports(java.lang.Object, java.lang.Class)
         */
        @Override
        public boolean supports(Object source, Class<?> targetType) {
            return source instanceof Map;
        }
    }

    private enum PropertyAccessingMethodInvokerFactory implements MethodInterceptorFactory {

        INSTANCE;

        /*
         * (non-Javadoc)
         * @see org.springframework.data.projection.MethodInterceptorFactory#createMethodInterceptor(java.lang.Object, java.lang.Class)
         */
        @Override
        public MethodInterceptor createMethodInterceptor(Object source, Class<?> targetType) {
            return new PropertyAccessingMethodInterceptor(source);
        }

        /*
         * (non-Javadoc)
         * @see org.springframework.data.projection.MethodInterceptorFactory#supports(java.lang.Object, java.lang.Class)
         */
        @Override
        public boolean supports(Object source, Class<?> targetType) {
            return true;
        }
    }

    /**
     * Custom {@link MethodInterceptor} to expose the proxy target class even if we set
     * {@link ProxyFactory#setOpaque(boolean)} to true to prevent properties on {@link Advised} to be rendered.
     *
     * @author Oliver Gierke
     */
    private static class TargetAwareMethodInterceptor implements MethodInterceptor {

        private static final Method GET_TARGET_CLASS_METHOD;
        private static final Method GET_TARGET_METHOD;

        static {
            try {
                GET_TARGET_CLASS_METHOD = TargetAware.class.getMethod("getTargetClass");
                GET_TARGET_METHOD = TargetAware.class.getMethod("getTarget");
            } catch (NoSuchMethodException e) {
                throw new IllegalStateException(e);
            }
        }

        private final Class<?> targetType;

        public TargetAwareMethodInterceptor(Class<?> targetType) {

            Assert.notNull(targetType, "Target type must not be null");
            this.targetType = targetType;
        }

        /*
         * (non-Javadoc)
         * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
         */
        @Nullable
        @Override
        public Object invoke(@SuppressWarnings("null") MethodInvocation invocation) throws Throwable {

            if (invocation.getMethod().equals(GET_TARGET_CLASS_METHOD)) {
                return targetType;
            } else if (invocation.getMethod().equals(GET_TARGET_METHOD)) {
                return invocation.getThis();
            }

            return invocation.proceed();
        }
    }

}

