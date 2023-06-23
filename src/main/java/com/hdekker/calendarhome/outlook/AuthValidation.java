package com.hdekker.calendarhome.outlook;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

/***
 * To allow validation of authentication response.
 * 
 * @author Hayden Dekker
 *
 */
@Component
@VaadinSessionScope
public class AuthValidation {
	
	String state = UUID.randomUUID().toString();
    String nonce = UUID.randomUUID().toString();
    
    @Autowired
    AuthRedirect authRedirect;

    public void set(){
    	state = UUID.randomUUID().toString();
        nonce = UUID.randomUUID().toString();
    }

	public String getState() {
		return state;
	}

	public String getNonce() {
		return nonce;
	}
	
	public String getAuthRedirectURL() {
		return authRedirect.redirectForAuthentication(this);
	}
	
}
