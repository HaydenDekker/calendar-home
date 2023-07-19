package com.hdekker.calendarhome.outlook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hdekker.calendarhome.oauth.Authentication;
import com.microsoft.graph.requests.CalendarCollectionPage;
import com.microsoft.graph.requests.EventCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;

import okhttp3.Request;

@Component
public class OutlookCalendarAdapter implements CalendarPort {

	Logger log = LoggerFactory.getLogger(OutlookCalendarAdapter.class);
	
	@Override
	public List<CalendarEvent> getEvents(Authentication authentication) {
		
		GraphServiceClient<Request> client = GraphServiceClient.builder()
			.authenticationProvider(url-> {
				
				log.info("Requesting auth for " + url);
				log.info("Providing barer " + authentication.accessToken().accessToken());
				log.info("Scopes " + authentication.accessToken().scopes());
				
				return CompletableFuture.completedFuture(
							authentication.accessToken().accessToken()
							);
				
			})
			.buildClient();
		
		  EventCollectionPage res = client.me()
				.events()
				.buildRequest()
				.get();
		  
		  DateTimeFormatter dtf = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
				
		  List<CalendarEvent> events = res.getCurrentPage()
				.stream()
				.map(e-> {
					return new CalendarEvent(
							e.subject, 
							e.body.content, 
							LocalDateTime.parse(e.start.dateTime),
							LocalDateTime.parse(e.end.dateTime));
				})
				.collect(Collectors.toList());
		  
		  log.info(events.size() + "events received.");
		
		return events;
	}

}