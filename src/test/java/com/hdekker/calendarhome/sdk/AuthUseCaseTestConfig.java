package com.hdekker.calendarhome.sdk;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.AuthenticationPort;

@Profile(TestProfiles.AUTH_SYSTEM)
@Configuration
public class AuthUseCaseTestConfig {

	@Bean
	@Primary
	public AuthenticationPort getAuthPort() {
		return Mockito.mock(AuthenticationPort.class);
	}
	
}
