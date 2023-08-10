package com.hdekker.calendarhome.outlook;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.oauth.AuthenticationService;

import jakarta.annotation.PostConstruct;

@Service
public class CalendarEventStream {
	
	List<Consumer<CalendarEvent>> consumers = new ArrayList<>();
	
	public void listen(Consumer<CalendarEvent> consumer) {
		consumers.add(consumer);
	}
	
	public void fireEvent(CalendarEvent event) {
		consumers.forEach(c -> c.accept(event));
	}
	
	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	CalendarPort calendarPort;
	
	@PostConstruct
	public void postConstruct() {
		
		authenticationService.listenForUserAuthentication(auth->{
			
			calendarPort.getEvents(auth)
				.forEach(ce->fireEvent(ce));
			
		});
		
	}
	
}
