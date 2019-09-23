package com.sliit.ssd.application;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadDTO {

	private MultipartFile multipartFile;

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
}
