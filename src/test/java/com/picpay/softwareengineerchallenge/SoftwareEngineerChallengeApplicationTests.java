package com.picpay.softwareengineerchallenge;

import com.picpay.softwareengineerchallenge.configs.CollectionIndexConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class SoftwareEngineerChallengeApplicationTests {

	@MockBean
	private CollectionIndexConfig collectionIndexConfig;

	@Test
	void contextLoads() {
	}

}
