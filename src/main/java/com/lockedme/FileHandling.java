package com.lockedme;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FileHandling { // TODO maybe with the execute around pattern or as interface?
	
	public static Optional<File> searchFile(File directory, String filename) {
		assert(directory.isDirectory());
		return searchFileRec(directory, filename);
	}
	
	private static Optional<File> searchFileRec(File directory, String fileName) {
		assert(directory.isDirectory());
		for(File file : directory.listFiles()) {
			if (file.isFile() && file.getName() == fileName) {
				return Optional.of(file);
			} else if(file.isDirectory()) {
				Optional<File> fileSub = searchFileRec(file, fileName);
				if(fileSub.isPresent()) {
					return fileSub;
				}
			}
		}
		return Optional.empty();
		
	}
	
	public static Optional<File> searchFileFunc(File directory, String filename) throws IOException {
		assert(directory.isDirectory());
		return Files.
				walk(directory.
						toPath()).
				filter(Files::isRegularFile).
				filter(file -> file.getFileName().toString().equals(filename)).
				map(path -> path.toFile()).
				findFirst();
	}
	
	public static List<File> listFiles(File directory) {
		assert(directory.isDirectory());
		return Arrays.asList(directory.listFiles());
	}
	
	public static boolean deleteFileInDirectory(File directory, String filename) {
		File fileToDelete = new File(directory.getAbsolutePath() + File.pathSeparator + filename);
		if (fileToDelete.exists()) {
			return fileToDelete.delete();
		} else {
			return false;
		}
	}
	
	public static Optional<File> createFileInDirectory(File directory, String filename) throws IOException {
		File fileToCreate = new File(directory.getAbsolutePath() + File.pathSeparator + filename);
		return fileToCreate.createNewFile() ? Optional.of(fileToCreate) : Optional.empty();
	}
	
	public static Optional<File> createDirectoryInDirectory(File directory, String filename) throws IOException {
		File directoryToCreate = new File(directory.getAbsolutePath() + File.pathSeparator + filename);
		return directoryToCreate.createNewFile() ? Optional.of(directoryToCreate) : Optional.empty();
	}
	
	public static boolean checkIfFileExists(File file) {
		assert(file.isFile());
		return file.exists();
	}
	
	public static boolean checkIfDirectoryExists(File directory) {
		assert(directory.isDirectory());
		return directory.exists();
	}
	
}