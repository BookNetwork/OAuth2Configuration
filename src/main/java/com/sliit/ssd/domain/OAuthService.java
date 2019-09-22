package com.sliit.ssd.domain;

import com.google.api.client.auth.oauth2.Credential;

public interface OAuthService {
	
	public Credential credentials();
	
	public String authenticateUser();
	
	public String tokenExchange();
	
	public String discardUserSession();
	
	public String userAuthenticationStatus(); 
	
}
