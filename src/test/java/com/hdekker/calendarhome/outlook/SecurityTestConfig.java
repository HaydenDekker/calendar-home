package com.hdekker.calendarhome.outlook;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.calendarhome.oauth.AccesTokenPort;

@Profile("auth-system-test")
@Configuration
public class SecurityTestConfig {

	@Bean
	@Primary
	public AccesTokenPort getAuthPort() {
		return Mockito.mock(AccesTokenPort.class);
	}
	
}
