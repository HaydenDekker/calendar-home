package com.hdekker.calendarhome.outlook;

import java.time.LocalDateTime;

import com.hdekker.calendarhome.oauth.Authentication;

public record CalendarEvent(
		Authentication auth,
		String subject, 
		String body,
		LocalDateTime startTime,
		LocalDateTime finishTime) {}
