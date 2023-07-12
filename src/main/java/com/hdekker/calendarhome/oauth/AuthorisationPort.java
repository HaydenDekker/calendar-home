package com.hdekker.calendarhome.oauth;

import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

/***
 * 
 * Manage incoming authorisation tokens
 * 
 * @author Hayden Dekker
 *
 */
@Service
public class AuthorisationPort {
	
	Logger log = LoggerFactory.getLogger(AuthorisationPort.class);
	
	@Autowired
	public AccesTokenPort accesTokenPort;
	
	public static final Integer MAX_WAITING_TIME_FOR_TOKEN = 5;
	
	public void getAccessTokens(Authorisation authorisation) {
		try {
			Mono<AccessToken> token = accesTokenPort.getAccess(authorisation);
			token.timeout(Duration.ofSeconds(MAX_WAITING_TIME_FOR_TOKEN))
				.subscribe(c->{
					fire(c);
				}, e->{
					log.error(e.getLocalizedMessage());
				}
				);
			
		} catch (URISyntaxException e) {
			log.error("URI Exception..");
			// TODO manage error. Flag to user.
		}
	}

	List<Consumer<AccessToken>> accessTokenListener = new ArrayList<>();
	
	public void listenForTokens(Consumer<AccessToken> listener) {
		accessTokenListener.add(listener);
	}
	
	void fire(AccessToken at){
		log.info("Firing auth token events to " + accessTokenListener.size() + " listeners.");
		accessTokenListener.forEach(c->c.accept(at));
	}
	
}
