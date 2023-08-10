package com.hdekker.calendarhome.oauth;

/***
 * Should store and provide the stored entity back.
 * 
 * @author Hayden Dekker
 *
 */
public interface AuthenticationPersistancePort {

	Authentication persist(Authentication auth);
	
}
