package com.hdekker.calendarhome.oauth;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.calendarhome.outlook.ClientApplication;
import com.microsoft.aad.msal4j.AuthorizationCodeParameters;
import com.microsoft.aad.msal4j.IAuthenticationResult;

import reactor.core.publisher.Mono;

/**
 * 
 * Retrieves AccessTokens
 * 
 * @author Hayden Dekker
 *
 */
@Service
public class AccesTokenPort {
	
	Logger log = LoggerFactory.getLogger(AccesTokenPort.class);
	
	ObjectMapper om = new ObjectMapper();

	@Autowired
	ClientApplication ca;
	
	public Mono<AccessToken> getAccess(Authorisation authorisation) throws URISyntaxException {
		
		AuthorizationCodeParameters parameters = AuthorizationCodeParameters.builder(
			        authorisation.code(),
			        new URI(authorisation.currentURI())).
			        build();
	

        return Mono.fromCompletionStage(ca.getClientApplication()
        		.acquireToken(parameters)
        		.thenApply(ar-> {
	        		
					log.info("Obtained access token.");
					log.info("User Acc " + ar.account().username());
        			
	        		return new AccessToken(ar.accessToken(),
	        				ar.idToken(), 
	        				ar.scopes(),
	        				ar.expiresOnDate()
	        					.toInstant()
	        					.atZone(ZoneId.systemDefault())
	        					.toLocalDate());
        		}));
		
	}
	
}
