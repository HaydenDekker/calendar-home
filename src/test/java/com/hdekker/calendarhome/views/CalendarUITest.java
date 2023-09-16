package com.hdekker.calendarhome.views;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.calendarhome.TestProfiles;
import com.hdekker.calendarhome.oauth.AccessToken;
import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.outlook.CalendarEvent;
import com.hdekker.calendarhome.outlook.CalendarEventStream;
import com.hdekker.calendarhome.sdk.WebDriverConfig;
import com.microsoft.graph.models.Location;
import com.microsoft.graph.models.PhysicalAddress;

/***
 * Mocks calendar event stream for UI
 * to easily develop UI look and feel.
 * 
 * @author Hayden Dekker
 *
 */
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles(TestProfiles.UI)
public class CalendarUITest {
	
	Logger log = LoggerFactory.getLogger(CalendarUITest.class);

	@Autowired
	WebDriverConfig config;
	
	@Autowired
	CalendarEventStream calendarEventStream;
	
	public List<CalendarEvent> testEvents() {
		
		ObjectMapper om = new ObjectMapper();
		
		Location pa = null;
		
		try {
			pa = om.readValue("{\"address\":{\"city\":\"Melbourne\"}}", Location.class);
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		assertThat(pa.address.city).isEqualTo("Melbourne");
		
		return List.of(
				
				new CalendarEvent(
					// TODO do I really need this Auth in calendar....
					new Authentication(
							new AccessToken("SDD", "SDDF", "DFCF", LocalDate.now()),
							"HAppy ayden"), 
					"Subject Test", "This is what you should display",
					pa,
					LocalDateTime.now(),
					LocalDateTime.now().plusDays(1)),
				new CalendarEvent(
						// TODO do I really need this Auth in calendar....
						new Authentication(
								new AccessToken("SDD", "SDDF", "DFCF", LocalDate.now()),
								"HAppy ayden"), 
						"Ahh yesh", "Must have to..",
						pa,
						LocalDateTime.now(),
						LocalDateTime.now().plusDays(1))
				
				
		
		);
		
		
	}
	
	@Test
	@DisplayName("Mockito should allow fireing data to a listener")
	public void firesData() {
		
		doAnswer(inv->{
			
			Consumer<CalendarEvent> eventConsumer = inv.getArgument(0);
			testEvents().forEach(ce->eventConsumer.accept(ce));
			return null;
			
		}).when(calendarEventStream)
		.listen(any(Consumer.class));

		calendarEventStream.listen(ce->{
			log.info("Calendar consumer called with event");
		});
		
	}
	
	@Test
	@DisplayName("Should display all fields of calendar record")
	public void displaysCalendarEventRecord() throws InterruptedException {
		
		doAnswer(inv->{
			
			Consumer<CalendarEvent> eventConsumer = inv.getArgument(0);
			testEvents().forEach(ce->eventConsumer.accept(ce));
			return null;
			
		}).when(calendarEventStream)
		.listen(any(Consumer.class));
		
		WebDriver webDriver = config.getWebDriver();
		webDriver.get("https://localhost:8080/calendar");
	
		Thread.sleep(100);
		
	}
	
	/**
	 * Throws NoSuchWindowException
	 * @param webDriver
	 * @return
	 */
	public String getWindowHandle(WebDriver webDriver){
		
		try{
			return webDriver.getWindowHandle();
		}catch(Exception e) {
			return null;
		}
		
	}
	
	
}
