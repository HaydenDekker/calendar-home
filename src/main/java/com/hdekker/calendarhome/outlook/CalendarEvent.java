package com.hdekker.calendarhome.outlook;

import java.time.LocalDateTime;

import com.hdekker.calendarhome.oauth.Authentication;
import com.microsoft.graph.models.Location;

public record CalendarEvent(
		Authentication auth,
		String subject, 
		String body,
		Location location,
		LocalDateTime startTime,
		LocalDateTime finishTime) {}
