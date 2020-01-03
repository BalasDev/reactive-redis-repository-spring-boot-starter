package com.repository.reactive.test;

import com.repository.reactive.ReactiveRedisRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends ReactiveRedisRepository<TestRedis, String>/*org.springframework.data.repository.Repository<String, String>*//*extends ReactiveMongoRepository*/ {
}
