package com.sliit.ssd.domain;

import java.sql.Driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.FileDataStoreFactory;

@Service
public class OAuthServiceImplementaion implements OAuthService {

	Logger logger = LoggerFactory.getLogger(OAuthServiceImplementaion.class);
	private GoogleAuthorizationCodeFlow flow;
	private FileDataStoreFactory dataStoreFactory;

	private Driver drive;


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
	public String userAuthenticationStatus() {
		// TODO Auto-generated method stub
		return null;
	}

}
