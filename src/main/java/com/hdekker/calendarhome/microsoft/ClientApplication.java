package com.hdekker.calendarhome.microsoft;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IClientSecret;
import com.microsoft.aad.msal4j.ITokenCacheAccessAspect;
import com.microsoft.aad.msal4j.ITokenCacheAccessContext;
import jakarta.annotation.PostConstruct;

/***
 * https://learn.microsoft.com/en-us/entra/msal/java/advanced/msal-java-token-cache-serialization
 * 
 * @author Hayden Dekker
 *
 */
@Configuration
public class ClientApplication implements ITokenCacheAccessAspect{
	
	Logger log = LoggerFactory.getLogger(ClientApplication.class);

	@Autowired
	BasicConfiguration bc;
	
	ConfidentialClientApplication pca;
	
	@Autowired
	TokenCachePeristancePort tokenCachePeristancePort;
	
	@Autowired
	TokenCacheLookupPort tokenCacheLookupPort;
	
	public ClientApplication(BasicConfiguration bc, TokenCachePeristancePort tokenCachePeristancePort,
			TokenCacheLookupPort tokenCacheLookupPort) {
		super();
		this.bc = bc;
		this.tokenCachePeristancePort = tokenCachePeristancePort;
		this.tokenCacheLookupPort = tokenCacheLookupPort;
	}

	@PostConstruct
	public void buildApp() throws MalformedURLException {
		
		IClientSecret cred = ClientCredentialFactory.createFromSecret(bc.getSecretKey());
		
		pca = ConfidentialClientApplication.builder(bc.getClientId(), cred)
				.setTokenCacheAccessAspect(this)
	     		.authority(bc.getAuthority())
	     		.build();
		
	}

	public ConfidentialClientApplication getClientApplication() {
		return pca;
	}

	@Override
	public void beforeCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext) {
		String cache = tokenCacheLookupPort.getCache()
			.block();
		log.info("Restoring cache " + cache);
		iTokenCacheAccessContext.tokenCache()
			.deserialize(cache);
	}

	@Override
	public void afterCacheAccess(ITokenCacheAccessContext iTokenCacheAccessContext) {
		String newCache = iTokenCacheAccessContext.tokenCache()
				.serialize();
		log.info("Saving cache " + newCache);
		tokenCachePeristancePort.persist(newCache);
	}
	
}
