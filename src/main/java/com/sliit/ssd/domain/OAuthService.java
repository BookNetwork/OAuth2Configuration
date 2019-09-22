package com.sliit.ssd.domain;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;

public interface OAuthService {
	
	public Credential credentials();
	
	public String authenticateUser();
	
	public String tokenExchange();
	
	public String discardUserSession();
	
	public boolean userAuthenticationStatus(); 
	
}
