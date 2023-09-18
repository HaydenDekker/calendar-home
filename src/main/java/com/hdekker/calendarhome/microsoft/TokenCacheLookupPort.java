package com.hdekker.calendarhome.microsoft;

import reactor.core.publisher.Mono;

public interface TokenCacheLookupPort {

	Mono<String> getCache();
	
}
