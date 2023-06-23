package com.hdekker.calendarhome.outlook;

/***
 * Object as per 
 * 
 * https://learn.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow
 * 
 * @author Hayden Dekker
 *
 */
public record Authorisation (String code, String state) {}
	
