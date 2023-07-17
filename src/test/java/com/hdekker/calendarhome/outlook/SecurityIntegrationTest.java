package com.hdekker.calendarhome.outlook;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthorisationPort;
import com.hdekker.calendarhome.sdk.UserAgentOauthSDK;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityIntegrationTest {
	
	Logger log = LoggerFactory.getLogger(SecurityIntegrationTest.class);
	
	@Value("${test.microsoft.user}")
	public String microSoftAccount;
	
	@Value("${test.microsoft.password}")
	public String microsoftAccountPassword;

	@Autowired
	UserAgentOauthSDK userAgentSDK;
	
	@Autowired
	AuthorisationPort authorisationPort;
	
	@Test
	@DisplayName("Can automate user agent to obtain authentication")
	public void automatesUserAgentAuthentication() throws InterruptedException {
		
		log.info("Using account " + microSoftAccount);
		
		Authentication auth = userAgentSDK.loginUser(microSoftAccount, microsoftAccountPassword);

		assertThat(auth).isNotNull();
		assertThat(auth.accessToken()).isNotNull();
	}
	
}
