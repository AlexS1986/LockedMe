package com.lockedme;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Directory extends File {

	private List<File> files = new LinkedList<File>();
	
	public Directory(String name) {
		super(name);
	}
	
	@Override
	public boolean isDirectory() {
		return true;
	}
	
	/**
	 * TODO should be a deep copy
	 * @return
	 */
	public List<File> listAscendingFiles() {
		//files.sort((f1,f2) -> f1.getName().compareTo(f2.getName()));
		return files.stream().sorted((f1,f2) -> f1.getName().compareTo(f2.getName())).collect(Collectors.toList());
	}
	

	public boolean addFile(File file) {
		if (!files.contains(file)) {
			files.add(file);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean deleteFile(File file) {
		if (files.contains(file)) {
			files.remove(file);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean addDirectory(Directory directory) {
		files.add(directory);
		return true;
	}
	
}
