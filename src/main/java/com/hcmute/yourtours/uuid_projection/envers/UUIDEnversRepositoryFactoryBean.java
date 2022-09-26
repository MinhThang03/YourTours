package com.hcmute.yourtours.uuid_projection.envers;

import com.hcmute.yourtours.uuid_projection.UUIDProjectionRepositoryFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryImpl;
import org.springframework.data.envers.repository.support.ReflectionRevisionEntityInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.history.support.RevisionEntityInformation;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UUIDEnversRepositoryFactoryBean<T extends RevisionRepository<S, ID, N>, S, ID, N extends Number & Comparable<N>>
        extends EnversRevisionRepositoryFactoryBean<T, S, ID, N> {
    private Class<?> revisionEntityClass;

    public UUIDEnversRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    public void setRevisionEntityClass(Class<?> revisionEntityClass) {
        this.revisionEntityClass = revisionEntityClass;
        super.setRevisionEntityClass(revisionEntityClass);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new UUIDRevisionRepositoryFactory<T, ID, N>(entityManager, revisionEntityClass);
    }

    private static class UUIDRevisionRepositoryFactory<T, ID, N extends Number & Comparable<N>> extends UUIDProjectionRepositoryFactory {

        private final RevisionEntityInformation revisionEntityInformation;
        private final EntityManager entityManager;

        public UUIDRevisionRepositoryFactory(EntityManager entityManager, Class<?> revisionEntityClass) {

            super(entityManager);

            this.entityManager = entityManager;
            this.revisionEntityInformation = Optional.ofNullable(revisionEntityClass) //
                    .filter(it -> !it.equals(DefaultRevisionEntity.class))//
                    .<RevisionEntityInformation>map(ReflectionRevisionEntityInformation::new) //
                    .orElseGet(DefaultRevisionEntityInformation::new);
        }

        @Override
        protected RepositoryComposition.RepositoryFragments getRepositoryFragments(RepositoryMetadata metadata) {

            Object fragmentImplementation = instantiateClass(
                    EnversRevisionRepositoryImpl.class,
                    getEntityInformation(metadata.getDomainType()),
                    revisionEntityInformation,
                    entityManager
            );
            return RepositoryComposition.RepositoryFragments
                    .just(fragmentImplementation)
                    .append(super.getRepositoryFragments(metadata));
        }
    }
}
