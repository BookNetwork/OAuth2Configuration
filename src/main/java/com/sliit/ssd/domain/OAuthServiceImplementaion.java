package com.sliit.ssd.domain;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.DriveScopes;
import com.sliit.ssd.infrastructure.ExternalPropConfig;
import com.sliit.ssd.utils.Constant;

@Service
public class OAuthServiceImplementaion implements OAuthService {

	Logger logger = LoggerFactory.getLogger(OAuthServiceImplementaion.class);

	HttpTransport httpTransport = new NetHttpTransport();
	JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	List<String> scope = Collections.singletonList(DriveScopes.DRIVE);

	private GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow;
	private FileDataStoreFactory dataStoreFactory;
	
	@Autowired
	ExternalPropConfig externalPropConfig;

	/**
	 * 
	 */
	@Override
	public Credential credentials() throws IOException {
		
		return googleAuthorizationCodeFlow.loadCredential(Constant.USER_KEY);

	}

	/**
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	public void authorizationPreConfiguration() throws IOException {
		logger.info("AUTHORIZATION CONFIG STARTED");

		InputStreamReader reader = new InputStreamReader(externalPropConfig.getSecretKeyFilePath().getInputStream());
		dataStoreFactory = new FileDataStoreFactory(externalPropConfig.getCredentialsPath().getFile());

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, reader);

		googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scope)
				.setDataStoreFactory(dataStoreFactory).build();

		logger.info("AUTHORIZATION CONFIG DONE");
		

	}

	/**
	 * 
	 */
	@Override
	public String authenticateUser() throws IOException {

		GoogleAuthorizationCodeRequestUrl url = googleAuthorizationCodeFlow.newAuthorizationUrl();
		String redirectUrl = url.setRedirectUri(externalPropConfig.getRedirectionURI()).setAccessType("offline")
				.build();

		return redirectUrl;
	}

	/**
	 * 
	 */
	@Override
	public void tokenExchange(String CODE) throws IOException {

		GoogleTokenResponse tokenResponse = googleAuthorizationCodeFlow.newTokenRequest(CODE)
				.setRedirectUri(externalPropConfig.getRedirectionURI()).execute();
		googleAuthorizationCodeFlow.createAndStoreCredential(tokenResponse, Constant.USER_KEY);
	}

	@Override
	public void discardUserSession(HttpServletRequest request) throws IOException {

		dataStoreFactory.getDataStore(externalPropConfig.getCredentialsPath().getFilename()).clear();
	}

	/**
	 * 
	 */
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
