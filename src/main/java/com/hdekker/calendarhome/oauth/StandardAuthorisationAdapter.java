package com.hdekker.calendarhome.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hdekker.calendarhome.outlook.Endpoints;

@RestController
public class StandardAuthorisationAdapter {
	
	Logger log = LoggerFactory.getLogger(StandardAuthorisationAdapter.class);
	
	@Autowired
	AuthorisationPort authorisationPort;

	@GetMapping(path = "/calendar-auth-resp")
	public void receiveAuthorisation(
				@RequestParam("code") String code,
				@RequestParam("state") String state
			) {
		
		log.info("Obtaining access tokens.");
		authorisationPort.getAccessTokens(new Authorisation(code, state, Endpoints.authorisation));
		
	}
	
}
