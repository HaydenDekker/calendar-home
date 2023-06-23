package com.hdekker.calendarhome.outlook;

import java.net.MalformedURLException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.microsoft.aad.msal4j.AuthorizationRequestUrlParameters;
import com.microsoft.aad.msal4j.Prompt;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.ResponseMode;

@Component
public class AuthRedirect {

	public final String redirectURL = "https://localhost:8080/calendar-auth-resp";
	
	public String redirectForAuthentication(AuthValidation authValidation) {
		
		try {
			String authorizationCodeUrl = getAuthorizationCodeUrl(
					redirectURL,
					authValidation.getState(), 
					authValidation.getNonce());
			return authorizationCodeUrl;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return "";
	       
		
	}
	
	@Autowired
	BasicConfiguration bc;
	
	private String getAuthorizationCodeUrl(String registeredRedirectURL, String state, String nonce)
            throws MalformedURLException {

        String updatedScopes = "";
        String claims = "";

        PublicClientApplication pca = PublicClientApplication.builder(bc.getClientId())
        		.authority(bc.getAuthority())
        		.build();

        AuthorizationRequestUrlParameters parameters =
                AuthorizationRequestUrlParameters
                        .builder(registeredRedirectURL,
                                Collections.singleton(updatedScopes))
                        .responseMode(ResponseMode.QUERY)
                        .prompt(Prompt.SELECT_ACCOUNT)
                        .state(state)
                        .nonce(nonce)
                        .claimsChallenge(claims)
                        .build();

        return pca.getAuthorizationRequestUrl(parameters).toString();
    }
	
}
