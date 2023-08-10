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
	CalendarEventStream calendarEventStream;
	
	// TODO this is the calendate display use case.
	@Test
	public void obtainTestUserCalendarEntries() {
		
		CalendarEvent res = (CalendarEvent) Mono.create(s->{
			
			// get graph client
			calendarEventStream.listen(event -> {
				log.info(event.subject() + event.body());
				s.success(event);
			});
			
			userAgentOauthSDK.loginUser();
			
		})
		.timeout(Duration.ofSeconds(30))
		.block();
		
		assertThat(res).isNotNull();
		assertThat(res.subject()).contains("Test1");
		
		
		
	}
	
}
