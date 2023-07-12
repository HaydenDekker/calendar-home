package com.hdekker.calendarhome.oauth;

import java.time.LocalDate;
import java.util.Date;

public record AccessToken(

	String accessToken,
	String idToken,
	String scopes,
	LocalDate expiresOn
	
) {}
