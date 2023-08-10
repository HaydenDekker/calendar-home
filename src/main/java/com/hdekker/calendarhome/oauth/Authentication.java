package com.hdekker.calendarhome.oauth;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public record Authentication(
		AccessToken accessToken, 
		String username) {

}
