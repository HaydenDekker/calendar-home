package com.hdekker.calendarhome.microsoft;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.oauth.AccessToken;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationPort;
import com.hdekker.calendarhome.oauth.Authorisation;
import com.microsoft.aad.msal4j.AuthorizationCodeParameters;

import reactor.core.publisher.Mono;

@Component
public class AuthenticationAdapter implements AuthenticationPort {
	
	@Autowired
	ClientApplication ca;
	
	Logger log = LoggerFactory.getLogger(AuthenticationAdapter.class);

	public Mono<Authentication> getAuthentication(Authorisation authorisation){
		
		AuthorizationCodeParameters parameters = null;
		try {
			parameters = AuthorizationCodeParameters.builder(
			        authorisation.code(),
			        new URI(Endpoints.authorisation))
					.scopes(AuthRedirect.scopes)
			        .build();
		} catch (URISyntaxException e) {
			log.error("Should never happen.");
		}


		return Mono.fromCompletionStage(ca.getClientApplication()
    		.acquireToken(parameters)
    		.thenApply(ar-> {
        		
				log.info("Obtained access token.");
				log.info("User Acc " + ar.account().username());
    			
        		return new Authentication(
        				new AccessToken(ar.accessToken(),
	        				ar.idToken(), 
	        				ar.scopes(),
	        				ar.expiresOnDate()
	        					.toInstant()
	        					.atZone(ZoneId.systemDefault())
	        					.toLocalDate()),
        				ar.account().username());
        		
    		}));
	}
	
}
