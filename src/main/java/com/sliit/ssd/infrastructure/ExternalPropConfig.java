package com.sliit.ssd.infrastructure;

import org.springframework.beans.factory.annotation.Value;		
import org.springframework.stereotype.Service;

@Service
public class ExternalPropConfig {

	@Value("${google.credentials}")
	private String credentialsPath;
	
	@Value("${google.redirection.uri}")
	private String redirectionURI;

	public String getCredentialsPath() {
		return credentialsPath;
	}

	public void setCredentialsPath(String credentialsPath) {
		this.credentialsPath = credentialsPath;
	}

	public String getRedirectionURI() {
		return redirectionURI;
	}

	public void setRedirectionURI(String redirectionURI) {
		this.redirectionURI = redirectionURI;
	} 
	
}
