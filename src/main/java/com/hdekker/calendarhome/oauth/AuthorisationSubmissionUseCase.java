package com.hdekker.calendarhome.oauth;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.UseCase;

import reactor.core.publisher.Mono;

/***
 * 
 * Manage incoming authorisation tokens
 * 
 * @author Hayden Dekker
 *
 */
@Service
public class AuthorisationSubmissionUseCase implements UseCase {
	
	Logger log = LoggerFactory.getLogger(AuthorisationSubmissionUseCase.class);
	
	@Autowired
	AuthenticationService authenticationService;
	
	public Mono<URI> submit(Authorisation authorisation) {
			return authenticationService.authenticate(authorisation);
	}
	
}
