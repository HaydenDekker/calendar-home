package com.hdekker.calendarhome.outlook;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.Authentication;

@Profile(TestProfiles.SYSTEM)
@Component
public class TestCalendarPort implements CalendarPort {

	Logger log = LoggerFactory.getLogger(TestCalendarPort.class);
	
	@Override
	public List<CalendarEvent> getEvents(Authentication authentication) {
		log.info("Auth received by test calendar port.");
		return List.of();
	}

}
