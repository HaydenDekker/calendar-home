package com.hdekker.calendarhome;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@DirtiesContext
@SpringBootTest
public class BaseTest {

	@Autowired
	ApplicationContext ctx;
	
	@Test
	public void ctxLoads() {
		assertThat(ctx, notNullValue());
	}
	
}
