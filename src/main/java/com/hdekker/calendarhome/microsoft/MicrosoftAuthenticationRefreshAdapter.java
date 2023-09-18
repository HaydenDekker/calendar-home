package com.hdekker.calendarhome.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.oauth.Authentication;

import reactor.core.publisher.Mono;

@Component
public class MicrosoftAuthenticationRefreshAdapter implements AuthenticationRefreshPort{

	@Autowired
	ClientApplication clientApplication;
	
	@Override
	public Mono<Authentication> refresh(Authentication authentication) {
		
		return null;
	}

}
