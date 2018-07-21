package com.team5.dl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public enum MockFile {

	SAMPLE_RESPONSE(
			MockFileLocation.JSON,
			"sampleResponse.json"
	),

	;

	private String fileLocation;
	private String fileName;
	
	private MockFile(MockFileLocation fileLocation, String fileName) {
		this.fileLocation = fileLocation.fileLocation();
		this.fileName = fileName;
	}

	public String load() {
		String contents = FileMocker.mock(fileLocation, fileName);
		assert contents != null;
		return contents;
	}

	public String fileLocation() {
		return fileLocation;
	}

	public String fileName() {
		return fileName;
	}
}

enum MockFileLocation {

	JSON("src/test/resources/json"),

	;
	
	private String fileLocation;
	
	private MockFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	
	public String fileLocation() {
		return fileLocation;
	}
	
}


