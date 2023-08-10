package com.hdekker.calendarhome.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.UseCase;

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
	
	public void submit(Authorisation authorisation) {
			authenticationService.authenticate(authorisation);
			
	}
	
}
