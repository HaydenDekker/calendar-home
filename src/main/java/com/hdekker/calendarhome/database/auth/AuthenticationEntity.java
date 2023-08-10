package com.hdekker.calendarhome.database.auth;

import com.hdekker.calendarhome.oauth.AccessToken;
import com.hdekker.calendarhome.oauth.Authentication;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "authentications")
public class AuthenticationEntity {

    @Id
    private String username;

    @Column(length = 10000)
    private String accessToken;
    
    @Column(length = 10000)
    private String idToken;
    
    private String scopes;

    @Column(name = "expires_on")
    private LocalDate expiresOn;

    public AuthenticationEntity() {
    }

    public AuthenticationEntity(Authentication authentication) {
        this.username = authentication.username();
        this.accessToken = authentication.accessToken().accessToken();
        this.idToken = authentication.accessToken().idToken();
        this.scopes = authentication.accessToken().scopes();
        this.expiresOn = authentication.accessToken().expiresOn();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public LocalDate getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(LocalDate expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Authentication toAuthentication() {
        AccessToken accessToken = new AccessToken(this.accessToken, this.idToken, this.scopes, this.expiresOn);
        return new Authentication(accessToken, username);
    }
    
}




