package com.hdekker.calendarhome.oauth;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hdekker.calendarhome.microsoft.BasicConfiguration;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationService{
	
	Logger log = LoggerFactory.getLogger(AuthenticationService.class);
	
	@Autowired
	public AuthenticationPort authenticationPort;
	
	@Autowired
	AuthenticationPersistancePort authenticationPersistancePort;
	
	@Autowired
	BasicConfiguration basicConfiguration;
	
	public static final Integer MAX_WAITING_TIME_FOR_TOKEN = 5;


	public Mono<URI> authenticate(Authorisation authorisation) {
		
		return Mono.create(sink->{
			Mono<Authentication> token = authenticationPort.getAuthentication(authorisation);
			token.timeout(Duration.ofSeconds(MAX_WAITING_TIME_FOR_TOKEN))
				.subscribe(auth->{
					
					log.info("Persisting authentication for " + auth.username());
					authenticationPersistancePort.persist(auth);
					fire(auth);
					URI uri = null;
					try {
						uri = new URI(basicConfiguration.getRedirectURLSigninSuccess());
					} catch (URISyntaxException e) {
						log.error("The configured URL is not conformant to the URI object.");
					}
					
					sink.success(uri);
					
				}, e->{
					log.error(e.getLocalizedMessage());
				}
				);
		});
		
		
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
