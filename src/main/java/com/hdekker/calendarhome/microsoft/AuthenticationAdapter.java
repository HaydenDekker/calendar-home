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
import com.microsoft.aad.msal4j.IAuthenticationResult;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationAdapter implements AuthenticationPort {
	
	@Autowired
	ClientApplication ca;
	
	Logger log = LoggerFactory.getLogger(AuthenticationAdapter.class);
	
	@Autowired
	BasicConfiguration basicConfiguration;
	
	public static Authentication convert(IAuthenticationResult result) {
		return new Authentication(
				new AccessToken(
    					result.accessToken(),
        				result.idToken(), 
        				result.scopes(),
        				result.expiresOnDate()
        					.toInstant()
        					.atZone(ZoneId.systemDefault())
        					.toLocalDate()),
    				result.account().username()
    				);
	}

	public Mono<Authentication> getAuthentication(Authorisation authorisation){
		
		AuthorizationCodeParameters parameters = null;
		try {
			parameters = AuthorizationCodeParameters.builder(
			        authorisation.code(),
			        // TODO should the uri be passed like this...?
			        new URI(basicConfiguration.getRedirectUriSignin()))
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
    			
        		return convert(ar);
        		
    		}));
	}
	
}
