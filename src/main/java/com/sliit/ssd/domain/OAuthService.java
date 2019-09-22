package com.sliit.ssd.domain;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.api.client.auth.oauth2.Credential;

public interface OAuthService {
	
	public Credential credentials() throws IOException;
	
	public String authenticateUser() throws IOException;
	
	public void tokenExchange(String CODE) throws IOException;
	
	public void discardUserSession(HttpServletRequest request) throws IOException;
	
	public String userAuthenticationStatus() throws IOException; 
	
}
