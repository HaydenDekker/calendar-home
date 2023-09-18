package com.hdekker.calendarhome.microsoft;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Supplier;
import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.ITokenCache;
import com.microsoft.aad.msal4j.ITokenCacheAccessAspect;
import com.microsoft.aad.msal4j.ITokenCacheAccessContext;

import reactor.core.publisher.Mono;

/**
 * MSAL cache not in my control.
 * Just a structural test to call database
 * ports.
 * 
 * @author Hayden Dekker
 *
 */
public class ClientApplicationTest {

	
	ITokenCacheAccessAspect cacheAspect;
	
	ITokenCacheAccessContext getContext() {
		return new ITokenCacheAccessContext() {
			
			@Override
			public ITokenCache tokenCache() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean hasCacheChanged() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public String clientId() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public IAccount account() {
				// TODO Auto-generated method stub
				return null;
			}
		};
	}
	
	Integer triggerFlag = 0;
	
	@Test
	@DisplayName("Client application needs to retrieve auth cache from database on startup for initialisation and should perisist the change when given.")
	public void whenStartingUp_ExpectCacheRetievedFromDB() {
		
		Supplier<Mono<String>> testHandle = ()-> {
			triggerFlag++;
			return Mono.just("IF called");
		};
		
		TokenCacheLookupPort tokenCacheLookupPort = mock(TokenCacheLookupPort.class);
		when(tokenCacheLookupPort.getCache())
			.thenReturn(testHandle.get());
		TokenCachePeristancePort tokenCachePeristancePort = mock(TokenCachePeristancePort.class);
		when(tokenCachePeristancePort.persist(anyString()))
			.thenReturn(testHandle.get());
		
		ClientApplication ca = new ClientApplication(null, tokenCachePeristancePort, tokenCacheLookupPort);
		
		ca.beforeCacheAccess(getContext());
		ca.afterCacheAccess(getContext());
		
		assertThat(triggerFlag).isEqualTo(2);
		
	}

}
