package com.hdekker.calendarhome.oauth;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationService{
	
	Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Autowired
	public AuthenticationPort authenticationPort;
	
	public static final Integer MAX_WAITING_TIME_FOR_TOKEN = 5;
	
	public void authenticate(Authorisation authorisation) {
		
		Mono<Authentication> token = authenticationPort.getAuthentication(authorisation);
		token.timeout(Duration.ofSeconds(MAX_WAITING_TIME_FOR_TOKEN))
			.subscribe(c->{
				fire(c);
			}, e->{
				log.error(e.getLocalizedMessage());
			}
			);
	}
	

	List<Consumer<Authentication>> accessTokenListener = new ArrayList<>();
	
	public void listenForUserAuthentication(Consumer<Authentication> listener) {
		accessTokenListener.add(listener);
	}
	
	void fire(Authentication at){
		log.info("Firing auth token events to " + accessTokenListener.size() + " listeners.");
		accessTokenListener.forEach(c->c.accept(at));
	}

	
}
