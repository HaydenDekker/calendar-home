package com.hdekker.calendarhome.microsoft;

import reactor.core.publisher.Mono;

public interface TokenCachePeristancePort {

	Mono<String> persist(String cache);
	
}
