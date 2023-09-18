package com.hdekker.calendarhome.microsoft;

import com.hdekker.calendarhome.oauth.Authentication;

import reactor.core.publisher.Mono;

public interface AuthenticationRefreshPort {
	Mono<Authentication> refresh(Authentication authentication);
}
