package com.sliit.ssd.domain;

import java.io.IOException;
import java.sql.Driver;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.sliit.ssd.infrastructure.ExternalPropConfig;
import com.sliit.ssd.utils.Constant;

@Service
public class OAuthServiceImplementaion implements OAuthService {

	Logger logger = LoggerFactory.getLogger(OAuthServiceImplementaion.class);

	private GoogleAuthorizationCodeFlow flow;
	private FileDataStoreFactory dataStoreFactory;

	private Driver drive;

	@Autowired
	GoogleIntegrationService googleConfig;

	@Autowired
	ExternalPropConfig externalPropConfig;

	@Override
	public Credential credentials() throws IOException {
		googleConfig.googleConfigForAuthorization(drive, flow, dataStoreFactory);
		return flow.loadCredential(Constant.USER_KEY);

	}

	@Override
	public String authenticateUser() throws IOException {

		googleConfig.googleConfigForAuthorization(drive, flow, dataStoreFactory);

		GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		String redirectUrl = url.setRedirectUri(externalPropConfig.getRedirectionURI()).setAccessType("offline")
				.build();

		return redirectUrl;
	}

	@Override
	public void tokenExchange(String CODE) throws IOException {

		googleConfig.googleConfigForAuthorization(drive, flow, dataStoreFactory);

		GoogleTokenResponse tokenResponse = flow.newTokenRequest(CODE)
				.setRedirectUri(externalPropConfig.getRedirectionURI()).execute();
		flow.createAndStoreCredential(tokenResponse, Constant.USER_KEY);
	}

	@Override
	public void discardUserSession(HttpServletRequest request) throws IOException {
		
		googleConfig.googleConfigForAuthorization(drive, flow, dataStoreFactory);
		dataStoreFactory.getDataStore(externalPropConfig.getCredentialsPath().getFilename()).clear();
	}

	@Override
	public String userAuthenticationStatus() throws IOException {

		Credential credential = credentials();
		if (credential != null) {
			credential.refreshToken();
			return "STATUS_GREEN";
		}
		return "STATUS_RED";
	}

}
