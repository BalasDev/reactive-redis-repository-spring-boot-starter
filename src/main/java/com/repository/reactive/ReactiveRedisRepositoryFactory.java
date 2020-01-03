package com.repository.reactive;

import java.io.Serializable;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.ReactiveRepositoryFactorySupport;
import org.springframework.lang.Nullable;

public class ReactiveRedisRepositoryFactory extends ReactiveRepositoryFactorySupport {

    private ReactiveRedisOperations<?,?> operations;
    private HashMapper<Object, String, Object> hashMapper;

    public ReactiveRedisRepositoryFactory(ReactiveRedisOperations<?, ?> operations, HashMapper<Object, String, Object> hashMapper) {
        this.operations = operations;
        this.hashMapper = hashMapper;
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return getEntityInformation(domainClass, null);
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        EntityInformation<?, Serializable> entityInformation = getEntityInformation(information.getDomainType(),
            information);
        return getTargetRepositoryViaReflection(information, operations, hashMapper);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleReactiveRedisRepository.class;
    }

    @SuppressWarnings("unchecked")
    // todo add implementation for a EntityInformation class
    private <T, ID> MongoEntityInformation<T, ID> getEntityInformation(Class<T> domainClass,
                                                                       @Nullable RepositoryMetadata metadata) {
        return null;
        /*MongoPersistentEntity<?> entity = mappingContext.getRequiredPersistentEntity(domainClass);

        return new MappingRedisEntityInformation((MongoPersistentEntity<T>) entity,
            metadata != null ? (Class<ID>) metadata.getIdType() : null);*/
    }
}
