package com.hdekker.calendarhome.outlook;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.hdekker.calendarhome.sdk.OauthClientWebClient;

import reactor.core.publisher.Mono;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class SecurityTest {
	
	Logger log = LoggerFactory.getLogger(SecurityTest.class);

	static final String REDIRECT_URL = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize";
	
	@Autowired
	AuthRedirect authRedirect;
	
	/**
	 * This test will assert what the user will be redirected to
	 * when attempting to subscribe.
	 * 
	 * 
	 */
	@Test
	public void whenSubscribing_ExpectRedirectToMicrosoftAuth() {
		
		AuthValidation av = new AuthValidation();
		av.set();
		String redirectURL = authRedirect.redirectForAuthentication(av);
		log.info("Redirect URL created is " + redirectURL);	
		assertThat(redirectURL, containsString(REDIRECT_URL));
		
	}
	
	String authResponseStub = "https://localhost:8080/calendar-auth-resp?code=M.C105_BAY.2.9561f489-28e7-e342-a443-e11f5e179474&state=7af249ed-9724-4d5c-b737-f1a8428c5bb2";
	String code = "M.C105_BAY.2.9561f489-28e7-e342-a443-e11f5e179474";
	String state = "7af249ed-9724-4d5c-b737-f1a8428c5bb2";
	
	@Autowired
	OauthClientWebClient webClient;

	@DisplayName("When a response is given from the Auth server to the response URI, the app should receive the code and should be able to decode the response.")
	@Test
	public void whenAuthorisationResponseGiven_ExpectCodeOrErrorIsRead() {
		
		WebClient client = webClient.testClient()
			.baseUrl("https://localhost:8080/calendar-auth-resp")
			.build();
		
		HttpStatusCode status = client.get()
			.uri(b->
				b.queryParam("code", code)
					.queryParam("state", state)
					.build()
			)
			.exchangeToMono(cr->
				Mono.just(cr.statusCode())
			)
			.block();
		
		assertThat(status.value(), equalTo(200));
		
	}
	
	@Test
	public void whenUserConnectsCalendar_ExpectTokenStored() {
		
		
	}
	
	@Test
	public void whenTokenDueForExpiry_ExpectAppRenewsToken() {
		
	}
	
	@Test
	public void whenTokenFails_ExpectAppNotifiesUser() {
		
	}
	
}
