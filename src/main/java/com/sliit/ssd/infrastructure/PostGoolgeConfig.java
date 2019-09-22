package com.sliit.ssd.infrastructure;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.sliit.ssd.domain.OAuthService;
import com.sliit.ssd.utils.Constant;

@Service
public class PostGoolgeConfig {

	public static HttpTransport httpTransport = new NetHttpTransport();
	public static JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	@Autowired
	private ExternalPropConfig externalPropConfig;
	
	@Autowired
	private OAuthService oAuthService;

	@PostConstruct
	public void googleConfig(Drive drive) {
		Credential credential = oAuthService.credentials();
		drive = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(Constant.APPLICATION_NAME)
				.build();
	}

}
