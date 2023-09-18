package com.hdekker.calendarhome.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationPersistancePort;

import reactor.core.publisher.Mono;

@Service
public class AuthenticationRefreshService {

	@Autowired
	AuthenticationPersistancePort authenticationPersistancePort;
	
	@Autowired
	AuthenticationRefreshPort authenticationRefreshPort;
	
	public AuthenticationRefreshService(AuthenticationPersistancePort authenticationPersistancePort,
			AuthenticationRefreshPort authenticationRefreshPort) {
		super();
		this.authenticationPersistancePort = authenticationPersistancePort;
		this.authenticationRefreshPort = authenticationRefreshPort;
	}

	public Mono<Authentication> refreshToken(Authentication auth) {
		return authenticationRefreshPort.refresh(auth)
					.map(a->authenticationPersistancePort.persist(a));
	}
	
	

	
	
}
