package com.hdekker.calendarhome.outlook;

import java.time.LocalDateTime;

public record CalendarEvent(
		String subject, 
		String body,
		LocalDateTime startTime,
		LocalDateTime finishTime) {}
