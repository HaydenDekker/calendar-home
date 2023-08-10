package com.hdekker.calendarhome.outlook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.oauth.AuthenticationLookupPort;
import com.hdekker.calendarhome.oauth.AuthenticationService;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Service
public class CalendarEventStream {
	
	Logger log = LoggerFactory.getLogger(CalendarEventStream.class);
	
	List<Consumer<CalendarEvent>> consumers = new ArrayList<>();
	
	public void listen(Consumer<CalendarEvent> consumer) {
		consumers.add(consumer);
		refreshAll();
	}
	
	public void fireEvent(CalendarEvent event) {
		consumers.forEach(c -> c.accept(event));
	}
	
	@Autowired
	CalendarPort calendarPort;
	
	@Autowired
	AuthenticationLookupPort authenticationLookupPort;
	
	public void refreshAll() {
		
		Mono.create(s->{
			authenticationLookupPort.allAuthentication()
				.stream()
				.peek(c-> log.info("Getting auth for " + c.username()))
				.forEach(c->{
					calendarPort.getEvents(c)
						.forEach(ce->fireEvent(ce));
				});
		})
		.subscribe();
		
		
	}

	
}
