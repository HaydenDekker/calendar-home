package com.hdekker.calendarhome.oauth;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdekker.calendarhome.database.auth.AuthenticationEntity;
import com.hdekker.calendarhome.database.auth.AuthenticationRepository;

@SpringBootTest
public class AuthenticationRespositioryTest {
	
	Logger log = LoggerFactory.getLogger(AuthenticationRespositioryTest.class);
	
	@Autowired
	AuthenticationRepository authenticationRepository;
	
	public static final String TEST_USERNAME = "Fake-User-Name";

	@DisplayName("Can save authentication to configured database.")
	@Test
	public void authCanBeSaved() {
		
		Authentication auth = new Authentication(
								new AccessToken("Fake-Access-Token", 
										"hd", 
										"Fake-Read", 
										LocalDate.now()),
								TEST_USERNAME);
		
		authenticationRepository.save(new AuthenticationEntity(auth));
		
		Optional<Authentication> authOpt = authenticationRepository.findById("Fake-User-Name")
				.map(ae->ae.toAuthentication());
		
		assertThat(authOpt.isPresent()).isTrue();
		
	}
	
	@AfterEach
	public void clean() {
		
		authenticationRepository.deleteById(TEST_USERNAME);
		authenticationRepository.findById(TEST_USERNAME)
			.ifPresent(c->{
				log.info("Oops, test user was not deleted.");
			});
		
	}
	
}
