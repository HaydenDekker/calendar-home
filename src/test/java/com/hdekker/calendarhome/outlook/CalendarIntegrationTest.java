package com.hdekker.calendarhome.outlook;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.calendarhome.ApplicationProfiles;
import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationService;
import com.hdekker.calendarhome.sdk.CalendarUISDK;
import com.hdekker.calendarhome.sdk.UserAgentOauthSDK;

import reactor.core.publisher.Mono;

/**
 * Use token to retrieve user calendar entries.
 * 
 * @author Hayden Dekker
 *
 */
@DirtiesContext
@ActiveProfiles({ApplicationProfiles.PRODUCTION_ENV, TestProfiles.INTEGRATION})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CalendarIntegrationTest {
	
	Logger log = LoggerFactory.getLogger(CalendarIntegrationTest.class);

	@Autowired
	UserAgentOauthSDK userAgentOauthSDK;
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	CalendarEventStream calendarEventStream;
	
	@Autowired
	CalendarUISDK calendarUISDK;
	
	// TODO this is the calendar display use case.
	@Test
	public void obtainTestUserCalendarEntries() {
		
		// login user first to ensure up to date tokens
		Authentication res = (Authentication) Mono.create(s->{
			
			// get graph client
			authenticationService.listenForUserAuthentication(event -> {
				s.success(event);
			});
			
			userAgentOauthSDK.loginUser();
			
		})
		// TODO blocking in login stops this from occuring
		.timeout(Duration.ofSeconds(30))
		.block();
		
		assertThat(res).isNotNull();
		
		CalendarEvent ce = (CalendarEvent) Mono.create(s->{
			
			calendarEventStream.listen(c->{
				log.info(c.subject() + " found.");
				s.success(c);
			});
			
			calendarUISDK.openCalendar();
			
		})
		.timeout(Duration.ofSeconds(30))
		.block();
		
		
		String calendarItemContent = calendarUISDK.checkEventBySubjectExists(ce.subject());
		
		assertThat(calendarItemContent.concat(ce.subject()));
		
		
	}
	
}
