package com.lockedme;

import java.util.List;
import java.util.Optional;

public interface IModel {
	List<File> getFilesOfDirectory();
	boolean addFileToDirectory(String filename);
	boolean deleteFileFromDirectory(String filename);
	Directory getRootDirectory();
	void setRootDirectory(Directory rootDirectory);
	boolean addDirectoryToDirectory(String directoryName);
	boolean searchFileInDirectory(String filename);
}
