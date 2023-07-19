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

/***
 * 
 * Manage incoming authorisation tokens
 * 
 * @author Hayden Dekker
 *
 */
@Service
public class AuthorisationSubmissionUseCase {
	
	Logger log = LoggerFactory.getLogger(AuthorisationSubmissionUseCase.class);
	
	@Autowired
	AuthenticationService authenticationService;
	
	public void submit(Authorisation authorisation) {
			authenticationService.authenticate(authorisation);
			
	}
	
}
