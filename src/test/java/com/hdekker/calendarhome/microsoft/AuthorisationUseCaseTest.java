package com.hdekker.calendarhome.microsoft;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.AccessToken;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationDeletionPort;
import com.hdekker.calendarhome.oauth.AuthenticationLookupPort;
import com.hdekker.calendarhome.oauth.AuthenticationPort;
import com.hdekker.calendarhome.oauth.AuthenticationService;
import com.hdekker.calendarhome.oauth.Authorisation;
import com.hdekker.calendarhome.sdk.AuthCodeSupplier;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@DirtiesContext
@ActiveProfiles({TestProfiles.AUTH_SYSTEM, TestProfiles.SYSTEM})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class AuthorisationUseCaseTest {

		private static final String USERNAME = "TEST_USER_HAYDEN";

		Logger log = LoggerFactory.getLogger(AuthorisationUseCaseTest.class);
		
		@Value("${test.security.stubs.auth-response}")
		String authResponse;
		
		@Autowired
		AuthCodeSupplier authCodeSupplier;
		

		@Value("${test.security.stubs.code}")
		String code;

		@Value("${test.security.stubs.state}")
		String state;
		
		@DisplayName("If an error is sent from the authorisation service, we need to receive it.")
		@Disabled("Defer - Error Handling.")
		@Test
		public void whenAuthorisationResponseErrorGiven_ExpectErrorIsReceived() {
			
		}
		
		@DisplayName("Once auth received, we need to extract the response.")
		@Test
		public void whenAuthorisationReceived_ExpectMarshalledToAuthorisationObject() {
			
			Authorisation auth = new Authorisation(code, state);
			assertThat(auth.code(), equalTo(code));
			assertThat(auth.state(), equalTo(state));

		}
		
		@Autowired
		AuthenticationPort authenticationPort;
		
		@Autowired
		AuthenticationService	authenticationService;
		
		@Autowired
		AuthenticationLookupPort authenticationLookupPort;
		
		@Autowired
		AuthenticationDeletionPort authenticationDeletionPort;
		

		/**
		 * Asserts the api can accept auth codes 
		 * and then the application automatically 
		 * retrieves the access tokens.
		 * 
		 * 
		 * @throws URISyntaxException 
		 * @throws InterruptedException 
		 * 
		 * 
		 */
		@DisplayName("After auth is received, need to exchange for access tokens.")
		@Test
		public void givenAuthCode_ObtainsUserAccessInfo() throws URISyntaxException, InterruptedException {

			log.info(authenticationService.authenticationPort.toString() + " used by auth port.");
			
			// TODO disable other downstream listeners for this test.
			
			when(authenticationPort.getAuthentication(new Authorisation(code, state)))
				.thenReturn(Mono.just(new Authentication(
					new AccessToken("Random", "Me", "Some,Scopes", LocalDate.now()),
					USERNAME)));
			
			Mono<Authentication> atMono = Mono.create(s->{
				
				authenticationService.listenForUserAuthentication(at->{
					
					log.info("Received an access token with token value - " + at.accessToken());
					s.success(at);
					
				});
				
				log.info("Calling auth api using test sdk.");
				authCodeSupplier.sendAuthorisationMessage(code, state);
				
			});
			
			Authentication receivedAuth = atMono.take(Duration.ofSeconds(5))
					.publishOn(Schedulers.newBoundedElastic(10, 10, "auth-system-test"))
					.block();
			
			assertThat(receivedAuth, notNullValue());
			assertThat(receivedAuth.username(), equalTo(USERNAME));
			
			// database
			Optional<Authentication> savedEntity = authenticationLookupPort.allAuthentication()
						.stream()
						.filter(f-> f.username().equals(USERNAME))
						.findAny();
			
			assertThat(savedEntity.isPresent(), equalTo(true));
			
			savedEntity.ifPresent(c->{
				log.info("Deleting test user " + USERNAME);
				authenticationDeletionPort.delete(USERNAME);
			});
			
		}
		
		@Disabled
		// TODO renew tokens after expiry
		@Test
		public void whenTokenDueForExpiry_ExpectAppRenewsToken() {
			
		}
		
		@Disabled()
		// TODO communicate exceptions
		@Test
		public void whenTokenFails_ExpectAppNotifiesUser() {
			
		}
}
