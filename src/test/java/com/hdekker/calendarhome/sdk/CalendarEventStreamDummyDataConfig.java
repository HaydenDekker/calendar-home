package com.hdekker.calendarhome.sdk;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.outlook.CalendarEventStream;
import com.hdekker.calendarhome.outlook.CalendarPort;

@Configuration
@Profile(TestProfiles.UI)
public class CalendarEventStreamDummyDataConfig {
	
	Logger log = LoggerFactory.getLogger(CalendarEventStreamDummyDataConfig.class);

	@Bean
	@Primary
	public CalendarEventStream streamStub() {
		return Mockito.mock(CalendarEventStream.class);
		
	}
	
	/***
	 * Mocked for the above but should not be used.
	 * 
	 * @return
	 */
	@Bean
	public CalendarPort streamPortMock() {
		return (e)-> {
			log.error("This was mocked for the Calendar Event Stub and shouldn't be used");
			return null;
		};
	}
	
	
}
