package com.hdekker.calendarhome.oauth;

import java.net.URI;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StandardAuthorisationAdapter {
	
	Logger log = LoggerFactory.getLogger(StandardAuthorisationAdapter.class);
	
	@Autowired
	AuthorisationSubmissionUseCase authorisationSubmissionUseCase;

	
	@GetMapping(path = "/calendar-auth-resp")
	public ResponseEntity<Void> receiveAuthorisation(
				@RequestParam("code") String code,
				@RequestParam("state") String state
			) {
		
		log.info("Obtaining access tokens.");
		URI url = authorisationSubmissionUseCase.submit(new Authorisation(code, state))
			.take(Duration.ofSeconds(10))
			.block();
		
		log.info("redirecting user to " + url.toString());
		
		HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, url.toString());
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
		
		
	}
	
}
