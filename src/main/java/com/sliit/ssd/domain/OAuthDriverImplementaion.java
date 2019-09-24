package com.sliit.ssd.domain;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.sliit.ssd.infrastructure.ExternalPropConfig;
import com.sliit.ssd.utils.Constant;

@Service
public class OAuthDriverImplementaion {

	private Logger logger = LoggerFactory.getLogger(OAuthDriverImplementaion.class);

	HttpTransport httpTransport = new NetHttpTransport();
	JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	List<String> scope = Collections.singletonList(DriveScopes.DRIVE);

	private Drive drive;

	@Autowired
	OAuthService authorizationService;

	@Autowired
	ExternalPropConfig externalPropConfig;

	@PostConstruct
	public void driverConfig() throws Exception {
		logger.info("GOOGLE FRIVER CONFIG STARTED");

		Credential credential = authorizationService.credentials();
		drive = new Drive.Builder(httpTransport, jsonFactory, credential).setApplicationName(Constant.APP_NAME)
				.build();

		logger.info("GOOGLE FRIVER CONFIG END");
	}

	public void uploadFile(MultipartFile multipartFile) throws Exception {
		
		logger.info("FILE UPLOADING PROCESS STARTED");

		String path = externalPropConfig.getAppPath();
		String fileName = multipartFile.getOriginalFilename();
		String contentType = multipartFile.getContentType();

		java.io.File transferedFile = new java.io.File(path, fileName);
		multipartFile.transferTo(transferedFile);

		File fileMetadata = new File();
		fileMetadata.setName(fileName);

		FileContent mediaContent = new FileContent(contentType, transferedFile);
		File file = drive.files().create(fileMetadata, mediaContent).setFields("id").execute();

		logger.info("FILE UPLOADED SUCCESSFULLY");
	}

}
