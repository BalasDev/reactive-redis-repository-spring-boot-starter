package com.repository.reactive;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

@NoRepositoryBean
public interface ReactiveRedisRepository<T, ID> extends ReactiveCrudRepository<T, ID> {
}
