package com.hdekker.calendarhome.sdk;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

/***
 * Call oAuth endpoints during unit or system testing.
 * 
 * @author Hayden Dekker
 *
 */
@Component
public class OauthClientWebClient {

	public Builder testClient() {
		// Create a custom HttpClient with trust for self-signed certificates
	    HttpClient httpClient = HttpClient.create()
	            .secure(sslContextSpec -> sslContextSpec.sslContext(
	                    SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE)
	            ));
	
	    // Create a WebClient with the custom HttpClient
	    return WebClient.builder()
	            .clientConnector(new ReactorClientHttpConnector(httpClient));

	}
}
