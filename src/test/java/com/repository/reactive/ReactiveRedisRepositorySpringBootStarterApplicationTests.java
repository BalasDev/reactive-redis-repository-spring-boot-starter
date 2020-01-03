package com.repository.reactive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.repository.reactive.test.TestRedis;
import com.repository.reactive.test.TestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class only for checking the correct configuration of beans.
 * Before run it make sure that the Redis server is started on a local environment.
 */
@SpringBootTest
class ReactiveRedisRepositorySpringBootStarterApplicationTests {

	@Autowired
	private TestRepository testRepository;

	@Test
	void contextLoads() {
		String expectedId = "test:test";
		testRepository.save(new TestRedis(expectedId)).block();
		TestRedis testRedis = testRepository.findById(expectedId).block();
		assertNotNull(testRedis);
		assertEquals(expectedId, testRedis.getId());
	}

}
