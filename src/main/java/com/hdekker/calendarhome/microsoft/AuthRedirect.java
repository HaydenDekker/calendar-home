package com.hdekker.calendarhome.microsoft;

import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.UseCase;
import com.microsoft.aad.msal4j.AuthorizationRequestUrlParameters;
import com.microsoft.aad.msal4j.Prompt;
import com.microsoft.aad.msal4j.ResponseMode;

@Component
public class AuthRedirect implements UseCase {

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
	
	public static Set<String> scopes = Set.of("Calendars.Read", "openid", "profile");
	
	@Autowired
	ClientApplication clientApplication;
	
	private String getAuthorizationCodeUrl(String registeredRedirectURL, String state, String nonce)
            throws MalformedURLException {

        String claims = "";

        AuthorizationRequestUrlParameters parameters =
                AuthorizationRequestUrlParameters
                        .builder(registeredRedirectURL,
                                scopes)
                        .responseMode(ResponseMode.QUERY)
                        .prompt(Prompt.SELECT_ACCOUNT)
                        .state(state)
                        .nonce(nonce)
                        .claimsChallenge(claims)
                        .build();

        return clientApplication.getClientApplication()
        		.getAuthorizationRequestUrl(parameters).toString();
    }
	
}
