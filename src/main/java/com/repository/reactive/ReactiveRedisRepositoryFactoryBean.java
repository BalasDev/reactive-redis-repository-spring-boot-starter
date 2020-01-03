package com.repository.reactive;

import java.io.Serializable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class ReactiveRedisRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
    extends RepositoryFactoryBeanSupport<T, S, ID> {

    private @Nullable ReactiveRedisOperations<?,?> operations;
    private @Nullable HashMapper<Object, String, Object> hashMapper;

    /**
     * Creates a new {@link RepositoryFactoryBeanSupport} for the given repository interface.
     *
     * @param repositoryInterface must not be {@literal null}.
     */
    protected ReactiveRedisRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new ReactiveRedisRepositoryFactory(operations, hashMapper);
    }

    public void setReactiveRedisOperations(@Nullable ReactiveRedisOperations<?,?> operations) {

        Assert.notNull(operations, "ReactiveRedisOperations must not be null!");
        this.operations = operations;
    }

    public void setHashMapper(@Nullable HashMapper<Object, String, Object> hashMapper) {
        this.hashMapper = hashMapper;
    }
}
