package com.hdekker.calendarhome.microsoft;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.calendarhome.TestProfiles;

@DirtiesContext
@ActiveProfiles({TestProfiles.AUTH_SYSTEM, TestProfiles.SYSTEM})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RedirectUseCaseTest {
		
		Logger log = LoggerFactory.getLogger(RedirectUseCaseTest.class);
	
		@Value("${test.security.redirect-url}")
		String REDIRECT_URL;
		
		@Autowired
		AuthRedirect authRedirect;
		
		/**
		 * This test will assert what the user will be redirected to
		 * when attempting to subscribe.
		 * 
		 * 
		 */
		@DisplayName("Need to redirect user when they're subscribing.")
		@Test
		public void whenSubscribing_ExpectRedirectToMicrosoftAuth() {
			
			AuthValidation av = new AuthValidation();
			av.set();
			String redirectURL = authRedirect.redirectForAuthentication(av);
			log.info("Redirect URL created is " + redirectURL);	
			assertThat(redirectURL, containsString(REDIRECT_URL));
			
		}
		
	}
