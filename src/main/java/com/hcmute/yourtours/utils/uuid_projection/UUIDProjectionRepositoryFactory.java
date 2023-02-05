package com.hcmute.yourtours.utils.uuid_projection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.projection.ProjectionFactory;

import javax.persistence.EntityManager;

public class UUIDProjectionRepositoryFactory extends JpaRepositoryFactory {
    public UUIDProjectionRepositoryFactory(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected ProjectionFactory getProjectionFactory(ClassLoader classLoader, BeanFactory beanFactory) {
        UUIDProjection factory = new UUIDProjection();
        factory.setBeanClassLoader(classLoader);
        factory.setBeanFactory(beanFactory);
        return factory;
    }
}
