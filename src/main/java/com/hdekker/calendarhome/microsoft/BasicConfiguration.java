// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.hdekker.calendarhome.microsoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**!
 * Object containing configuration data for the application. Spring will automatically wire the
 * values by grabbing them from application.properties file
 */
@Component
@ConfigurationProperties("aad")
class BasicConfiguration {

    private String clientId;
    private String authority;
    
    @Value("${APP_REDIRECT_URL_SIGNIN}")
    private String redirectUriSignin;
    
    @Value("${APP_REDIRECT_URL_SIGNIN_SUCCESS}")
    private String redirectURLSigninSuccess;
    
    @Value("${APP_SECRET_KEY}")
    private String secretKey;
    private String msGraphEndpointHost;
    
    

    public String getRedirectURLSigninSuccess() {
		return redirectURLSigninSuccess;
	}

	public void setRedirectURLSigninSuccess(String redirectURLSigninSuccess) {
		this.redirectURLSigninSuccess = redirectURLSigninSuccess;
	}

	public String getAuthority(){
        if (!authority.endsWith("/")) {
            authority += "/";
        }
        return authority;
    }

    public String getClientId() {
        return clientId;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getRedirectUriSignin() {
        return redirectUriSignin;
    }

    public void setRedirectUriSignin(String redirectUriSignin) {
        this.redirectUriSignin = redirectUriSignin;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setMsGraphEndpointHost(String msGraphEndpointHost) {
        this.msGraphEndpointHost = msGraphEndpointHost;
    }

    public String getMsGraphEndpointHost(){
        return msGraphEndpointHost;
    }
    
    Logger log = LoggerFactory.getLogger(BasicConfiguration.class);
    
    @PostConstruct
    public void log() {
    	
    	log.info("SecretKey " + secretKey);
    	log.info("Redirect " + redirectUriSignin);
    	
    	
    }
}