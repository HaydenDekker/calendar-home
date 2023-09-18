package com.hdekker.calendarhome.outlook;

import java.util.List;

import com.hdekker.calendarhome.oauth.Authentication;

/***
 * Access calendar info given an authentication token
 * 
 * @author Hayden Dekker
 *
 */
public interface CalendarPort {

	public static class AuthException extends Exception{
		
	}
	
	List<CalendarEvent> getEvents(Authentication authentication) throws AuthException;
	
}
