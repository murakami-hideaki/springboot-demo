package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("dev") // ← これを追加！
class BatchSampleApplicationTests {

	@Test
	void contextLoads() {
	}

}
