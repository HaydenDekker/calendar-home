package com.hdekker.calendarhome.microsoft;

import java.net.MalformedURLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IClientSecret;
import jakarta.annotation.PostConstruct;

@Configuration
public class ClientApplication {
	
	Logger log = LoggerFactory.getLogger(ClientApplication.class);

	@Autowired
	BasicConfiguration bc;
	
	ConfidentialClientApplication pca;
	
	@PostConstruct
	public void buildApp() throws MalformedURLException {
		
		IClientSecret cred = ClientCredentialFactory.createFromSecret(bc.getSecretKey());
		
		pca = ConfidentialClientApplication.builder(bc.getClientId(), cred)
	     		.authority(bc.getAuthority())
	     		.build();
		
	}
	
	
	
	public ConfidentialClientApplication getClientApplication() {
		return pca;
	}
	
}
