package com.sliit.ssd.infrastructure;

import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.sliit.ssd.domain.OAuthService;
import com.sliit.ssd.utils.Constant;

@Service
public class GoolgeConfig {

	private Logger logger = LoggerFactory.getLogger(GoolgeConfig.class);

	public static HttpTransport httpTransport = new NetHttpTransport();
	public static JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	public static final List<String> scope = Collections.singletonList(DriveScopes.DRIVE);

	@Autowired
	private ExternalPropConfig externalPropConfig;

	@Autowired
	private OAuthService oAuthService;

	@PostConstruct
	public void googleConfigForDrive(Drive drive) {
		Credential credential = oAuthService.credentials();
		drive = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(Constant.APPLICATION_NAME)
				.build();
	}

	@PostConstruct
	public void googleConfigForAuthorization(Drive drive, GoogleAuthorizationCodeFlow flow, FileDataStoreFactory dataStoreFactory) {
		InputStreamReader reader = new InputStreamReader(externalPropConfig.getSecretKeyFilePath().getInputStream());
		dataStoreFactory = new FileDataStoreFactory(externalPropConfig.getCredentialsPath().getFile());

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, reader);
		flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scope)
				.setDataStoreFactory(dataStoreFactory).build();
	}

}
