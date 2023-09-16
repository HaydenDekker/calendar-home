package com.hdekker.calendarhome.outlook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.UseCase;
import com.hdekker.calendarhome.oauth.AuthenticationLookupPort;
import com.hdekker.calendarhome.oauth.AuthenticationService;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Mono;

@Service
public class CalendarEventStream implements UseCase{
	
	Logger log = LoggerFactory.getLogger(CalendarEventStream.class);
	
	List<Consumer<CalendarEvent>> consumers = new ArrayList<>();
	
	public void listen(Consumer<CalendarEvent> consumer) {
		consumers.add(consumer);
		refreshAll();
	}
	
	List<Consumer<CalendarEvent>> forRemoval = new ArrayList<>();
	
	public void fireEvent(CalendarEvent event) {
		
		consumers.forEach(c -> {
			try {
				c.accept(event);
			} catch (Exception e) {
				log.debug("Removing listener due to client error.");
				forRemoval.add(c);
			}
		});
		
		forRemoval.forEach(c->consumers.remove(c));
		forRemoval.clear();
		
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
