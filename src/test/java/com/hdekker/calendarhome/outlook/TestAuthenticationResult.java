package com.hdekker.calendarhome.outlook;

import java.util.Date;

import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.ITenantProfile;

public class TestAuthenticationResult implements IAuthenticationResult{

	@Override
	public String accessToken() {
		
		return null;
	}

	@Override
	public String idToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IAccount account() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITenantProfile tenantProfile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String environment() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String scopes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date expiresOnDate() {
		// TODO Auto-generated method stub
		return null;
	}

}
