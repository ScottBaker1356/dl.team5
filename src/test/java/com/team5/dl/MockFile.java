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


class FileMocker {

	private String contents;

	public FileMocker(String directoryPath, String fileName) {
		this.contents = mock(directoryPath, fileName);
	}

	public String getContents() {
		return this.contents;
	}

	public static String mock(String directoryPath, String fileName) {
		return read(formatDirectoryPath(directoryPath) + fileName);
	}

	private static String formatDirectoryPath(String directoryPath) {
		if (!directoryPath.endsWith("/")) {
			directoryPath = directoryPath + "/";
		}

		return directoryPath;
	}

	private static String read(String filename) {
		try {
			return tryToRead(filename);
		} catch (Exception var2) {
			return handleException(filename);
		}
	}

	private static String handleException(String filename) {
		return null;
	}

	private static String tryToRead(String filename) throws FileNotFoundException, IOException {
		File file = new File(filename);
		char[] responseData = new char[(int)file.length()];
		FileReader fileReader = new FileReader(file);
		fileReader.read(responseData);
		fileReader.close();
		return new String(responseData);
	}
}