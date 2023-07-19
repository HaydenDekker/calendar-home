package com.hdekker.calendarhome.calendarevent;

import java.util.List;
import java.util.stream.Collectors;

import com.hdekker.calendarhome.outlook.CalendarEvent;

// Simply order by start date.
public class CalenderEventListSorter {

	public static List<CalendarEvent> sortByDate(List<CalendarEvent> events) {
		return events.stream()
			.sorted((ce1, ce2) -> {
				return ce1.startTime().isBefore(ce2.startTime()) ? -1 : 1;
			})
			.collect(Collectors.toList());
	}

}
