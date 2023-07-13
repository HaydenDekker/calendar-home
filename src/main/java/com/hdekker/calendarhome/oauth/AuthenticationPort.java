package com.hdekker.calendarhome.oauth;

import reactor.core.publisher.Mono;

public interface AuthenticationPort {

	public Mono<Authentication> getAuthentication(Authorisation authorisation);
	
}
