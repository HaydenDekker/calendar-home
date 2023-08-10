package com.hdekker.calendarhome.database.auth;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.hdekker.calendarhome.oauth.Authentication;
import com.hdekker.calendarhome.oauth.AuthenticationDeletionPort;
import com.hdekker.calendarhome.oauth.AuthenticationLookupPort;
import com.hdekker.calendarhome.oauth.AuthenticationPersistancePort;

@Service
public class AuthenticationDatabaseService implements AuthenticationPersistancePort,
AuthenticationDeletionPort, AuthenticationLookupPort{

	@Autowired
	AuthenticationRepository authenticationRepository;
	
	@Override
	public Authentication persist(Authentication auth) {
		
		// TODO handle error.
		return authenticationRepository.save(new AuthenticationEntity(auth))
				.toAuthentication();

	}

	@Override
	public List<Authentication> allAuthentication() {
		
		return authenticationRepository.findAll()
				.stream()
				.map(ae->ae.toAuthentication())
				.collect(Collectors.toList());
	}

	@Override
	public void delete(String userName) {
		
		authenticationRepository.deleteById(userName);
		
	}

}
