package com.team5.dl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileMocker {

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