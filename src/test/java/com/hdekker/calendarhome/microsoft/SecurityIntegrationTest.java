package com.hdekker.calendarhome.microsoft;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthorisationSubmissionUseCase;
import com.hdekker.calendarhome.sdk.UserAgentOauthSDK;

@DirtiesContext
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityIntegrationTest {
	
	Logger log = LoggerFactory.getLogger(SecurityIntegrationTest.class);

	@Autowired
	UserAgentOauthSDK userAgentSDK;
	
	@Autowired
	AuthorisationSubmissionUseCase authorisationPort;
	
	@Test
	@DisplayName("Can automate user agent to obtain authentication")
	public void automatesUserAgentAuthentication() throws InterruptedException {
		
		Authentication auth = userAgentSDK.loginUser();

		assertThat(auth).isNotNull();
		assertThat(auth.accessToken()).isNotNull();
	}
	
}