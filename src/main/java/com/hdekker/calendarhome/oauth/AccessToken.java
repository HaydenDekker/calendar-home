package com.hdekker.calendarhome.oauth;

import java.time.LocalDate;

public record AccessToken(

	String accessToken,
	String idToken,
	String scopes,
	LocalDate expiresOn
	
) {}
