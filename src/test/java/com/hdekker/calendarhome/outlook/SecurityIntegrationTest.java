package com.hdekker.calendarhome.outlook;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

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
import com.hdekker.calendarhome.sdk.UserAgentSDK;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityIntegrationTest {
	
	Logger log = LoggerFactory.getLogger(SecurityIntegrationTest.class);
	
	@Value("${test.microsoft.user}")
	public String microSoftAccount;
	
	@Value("${test.microsoft.password}")
	public String microsoftAccountPassword;

	@Autowired
	UserAgentSDK userAgentSDK;
	
	@Autowired
	AuthorisationPort authorisationPort;
	
	@Test
	@DisplayName("Can automate user agent to obtain authentication")
	public void automatesUserAgentAuthentication() throws InterruptedException {
		
		log.info("Using account " + microSoftAccount);
		
		Mono<Authentication> auth = Mono.create(s->{
			
			authorisationPort.listenForUserAuthentication(a->{
				log.info("Authentication successful " + a.accessToken());
				s.success(a);
			});
			
			userAgentSDK.openSubscribe();
			userAgentSDK.clickSubscribe();
			userAgentSDK.enterUserEmailAndPasswordToMicrosoftOauth(microSoftAccount, microsoftAccountPassword);
			
		
		});
		
		Authentication res = auth
				.timeout(Duration.ofSeconds(20))
				.block();
		
		assertThat(res).isNotNull();
		assertThat(res.accessToken()).isNotNull();
	}
	
}
