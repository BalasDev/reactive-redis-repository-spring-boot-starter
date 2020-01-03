package com.repository.reactive;

import com.repository.reactive.test.TestRedis;
import java.io.Serializable;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.util.Assert;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SimpleReactiveRedisRepository<T, ID extends Serializable> implements ReactiveRedisRepository<T, ID> {

    private final @NonNull ReactiveRedisOperations<ID, T> redisTemplates;
    private final @NonNull HashMapper<Object, String, Object> hashMapper;

    @Override
    public <S extends T> Mono<S> save(S entity) {
        return redisTemplates.<String, Object>opsForHash()
            // explicitly set type for the test purposes
            .putAll((ID)((TestRedis)entity).getId(), hashMapper.toHash(entity))
            .doOnNext(b -> Assert.isTrue(b, "object is not saved"))
            .map(b -> entity);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> entityStream) {
        return null;
    }

    @Override
    public Mono<T> findById(ID id) {
        return redisTemplates.<String, Object>opsForHash()
            .entries(id)
            .collectMap(Map.Entry::getKey, Map.Entry::getValue)
            .filter(stringObjectMap -> stringObjectMap.size() > 0)
            .map(stringObjectMap -> (T) hashMapper.fromHash(stringObjectMap));
    }

    @Override
    public Mono<T> findById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return null;
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Flux<T> findAll() {
        return null;
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> ids) {
        return null;
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> idStream) {
        return null;
    }

    @Override
    public Mono<Long> count() {
        return null;
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> id) {
        return null;
    }

    @Override
    public Mono<Void> delete(T entity) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> entityStream) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
