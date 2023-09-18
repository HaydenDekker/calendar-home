package com.hdekker.calendarhome.database.tokencache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.microsoft.TokenCacheLookupPort;
import com.hdekker.calendarhome.microsoft.TokenCachePeristancePort;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
public class TokenCacheAdapter implements TokenCacheLookupPort, TokenCachePeristancePort {

	@Autowired
	TokenCacheRepository repo;
	
	Scheduler dbS = Schedulers.newBoundedElastic(4, 10, "token-db");
	
	@Override
	public Mono<String> persist(String cache) {
		
		TokenCacheEntity ent = new TokenCacheEntity();
		ent.setCache(cache);
		ent.setId(1);
		return Mono.just(repo.save(ent).getCache())
				.publishOn(dbS);
	}

	@Override
	public Mono<String> getCache() {
		return Mono.just(repo.findById(1).map(t->t.getCache()).orElse(""))
				.publishOn(dbS);
	}

}
