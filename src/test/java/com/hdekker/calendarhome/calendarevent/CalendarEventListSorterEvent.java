package com.hdekker.calendarhome.calendarevent;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.hdekker.calendarhome.outlook.CalendarEvent;

public class CalendarEventListSorterEvent {
	
	@Test
	public void sortsByDate() {
		
		CalendarEvent ce1 = new CalendarEvent(null, "Yippee", "Yes", null, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
		CalendarEvent ce2 = new CalendarEvent(null, "Yippee", "Yes", null, LocalDateTime.now().minusHours(5), LocalDateTime.now().minusHours(4));
		
		List<CalendarEvent> events = List.of(ce1, ce2);
		
		List<CalendarEvent> sorted = CalenderEventListSorter.sortByDate(events);
		assertThat(sorted).hasSize(2);
		assertThat(sorted.get(0)).isEqualTo(ce2);
		
	}
	
}
