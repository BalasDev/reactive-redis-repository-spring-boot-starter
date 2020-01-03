package com.repository.reactive;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import reactor.core.publisher.Flux;

@Configuration
@EnableAutoConfiguration
@ConditionalOnClass({ ReactiveRedisConnectionFactory.class, ReactiveRedisTemplate.class, Flux.class })
@AutoConfigureAfter(RedisReactiveAutoConfiguration.class)
@Import(ReactiveRedisRepositoryBeanDefinitionRegistrarSupport.class)
public class ReactiveRedisRepositoryConfig {

    // todo add condition
    @Bean
    @Qualifier("redisHashMapper")
    HashMapper<Object, String, Object> redisHashMapper() {
        return new Jackson2HashMapper(true);
    }

}
