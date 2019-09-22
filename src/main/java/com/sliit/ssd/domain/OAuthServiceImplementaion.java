package com.sliit.ssd.domain;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;
import org.springframework.stereotype.Service;

@Service
public class OAuthServiceImplementaion implements OAuthService{

	@Override
	public Credential credentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String authenticateUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tokenExchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String discardUserSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userAuthenticationStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
