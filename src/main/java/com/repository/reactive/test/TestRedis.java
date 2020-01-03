package com.repository.reactive.test;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

//@RedisHash("test")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestRedis {

    @Id
    private String id;
}
