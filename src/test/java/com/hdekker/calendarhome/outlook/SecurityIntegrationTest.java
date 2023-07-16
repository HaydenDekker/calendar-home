package com.hdekker.calendarhome.outlook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityIntegrationTest {

	@Test
	@DisplayName("Can automate user agent to obtain authentication")
	public void automatesUserAgentAuthentication() {
		
	}
	
}
