package com.hdekker.calendarhome.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StandardAuthorisationAdapter {
	
	Logger log = LoggerFactory.getLogger(StandardAuthorisationAdapter.class);
	
	@Autowired
	AuthorisationSubmissionUseCase authorisationSubmissionUseCase;

	@GetMapping(path = "/calendar-auth-resp")
	public void receiveAuthorisation(
				@RequestParam("code") String code,
				@RequestParam("state") String state
			) {
		
		log.info("Obtaining access tokens.");
		authorisationSubmissionUseCase.submit(new Authorisation(code, state));
		
		
		
	}
	
}
