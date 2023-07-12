package com.hdekker.calendarhome.sdk;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hdekker.calendarhome.outlook.Endpoints;

import reactor.core.publisher.Mono;

/**
 * Calls Authorisation API
 * 
 * @author Hayden Dekker
 *
 */
@Component
public class AuthCodeSupplier {

	@Autowired
	OauthClientWebClient webClient;
	
	public void sendAuthorisationMessage(String code, String state) {
		
		WebClient client = webClient.testClient()
				.baseUrl(Endpoints.authorisation)
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
	
}
