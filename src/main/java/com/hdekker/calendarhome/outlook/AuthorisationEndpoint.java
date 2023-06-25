package com.hdekker.calendarhome.outlook;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorisationEndpoint {
	
	Logger log = LoggerFactory.getLogger(AuthorisationEndpoint.class);

	@GetMapping(path = "/calendar-auth-resp")
	public void receiveAuthorisation(
				@RequestParam("code") String code,
				@RequestParam("state") String state
			) {
		log.info("Authorisation code received.");
		
		
		
	}
	
}
