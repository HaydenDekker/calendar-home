package com.hdekker.calendarhome.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.hdekker.calendarhome.TestEntity;
import com.hdekker.calendarhome.TestSchema;

@DirtiesContext
@SpringBootTest
public class DatabaseTest {
	
	@Autowired
	TestSchema testSchema;

	@Test
	public void canStoreAndRetrieve() {
		
		testSchema.save(new TestEntity(null, "test1"));
		TestEntity entity = testSchema.findById(1L).get();
		assertThat(entity.getName()).isEqualTo("test1");
		
	}
	
}
