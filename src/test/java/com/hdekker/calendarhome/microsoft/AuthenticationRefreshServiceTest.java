package com.hdekker.calendarhome.microsoft;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.hdekker.calendarhome.oauth.AccessToken;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationPersistancePort;

import reactor.core.publisher.Mono;

/**
 * It is up to clients using the auth token to
 * detect unauthorised and then attempt
 * refresh using the authentication refresh
 * service.
 * 
 * Ideally the refresh service would renew before
 * the expired token was used.
 * 
 * @SpringBootTest
 * @author Hayden Dekker
 *
 */
public class AuthenticationRefreshServiceTest {
	
	@DisplayName("Refresh token on demand.")
	@Test
	public void whenRequested_ExpectAquiresNewToken() {
		
		Authentication authResp = new Authentication(new AccessToken("3456", "2345", "sdc,df", LocalDate.now()), "Hayden.d@hotmail.com");
		AuthenticationPersistancePort persistance = mock(AuthenticationPersistancePort.class);
		AuthenticationRefreshPort refreshPort = mock(AuthenticationRefreshPort.class);
		Authentication auth = new Authentication(new AccessToken("1234", "2345", "sdc,df", LocalDate.now()), "Hayden.d@hotmail.com");
		when(refreshPort.refresh(auth)).thenReturn(Mono.just(authResp));
		when(persistance.persist(authResp)).thenReturn(authResp);
		
		AuthenticationRefreshService ars = new AuthenticationRefreshService(persistance, refreshPort);		
		Authentication result = ars.refreshToken(auth).block();
		
		assertThat(result.accessToken().accessToken()).isEqualTo("3456");
		
	}

	

	
}
