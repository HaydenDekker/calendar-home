package com.hdekker.calendarhome.microsoft;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.oauth.Authentication;
import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.SilentParameters;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Component
public class MicrosoftAuthenticationRefreshAdapter implements AuthenticationRefreshPort{

	Logger log = LoggerFactory.getLogger(MicrosoftAuthenticationRefreshAdapter.class);
	
	@Autowired
	ClientApplication clientApplication;
	
	private Mono<IAccount> convert(Authentication authentication) {
		return Mono.fromCompletionStage(clientApplication.getClientApplication()
					.getAccounts())
				.map(set-> set.stream()
						// TODO add additional user id checks
						.filter(acc->acc.username().equals(authentication.username()))
						.findAny()
						.orElseThrow()
						
				);
	}
	
	@Override
	public Mono<Authentication> refresh(Authentication authentication) {
		
		log.info("Attempting refresh for user " + authentication.username());
		
		return convert(authentication)
			.map(acc->{
				
				log.info("Account found in cache, extracting..");
				// TODO this requires a set for scope while, IAuthResult only 
				// returns a string with commas seperated values.
				Set<String> scopes = new HashSet<>(Arrays.asList(authentication.accessToken()
						.scopes()
						.split(",")
					));
				SilentParameters p = SilentParameters.builder(scopes, acc)
						.build();
				return p;
			})
			.flatMap(sp-> {
				try {
					return Mono.fromCompletionStage(clientApplication.getClientApplication()
							.acquireTokenSilently(sp))
							.doOnNext(r->{
								log.info("Result found for " + r.account().username() + " and access token " + r.accessToken());
								if(r.accessToken().equals(authentication.accessToken().accessToken())) {
									log.warn("Access token refresh requested but token returned was the same.");
								}
							});
				} catch (MalformedURLException e) {
					
					e.printStackTrace();
					return Mono.error(e);
				}
			})
			.map(res-> AuthenticationAdapter.convert(res));

	}
	
	@PostConstruct
	public void init() {
		log.info("Refresher started.");
	}

}
