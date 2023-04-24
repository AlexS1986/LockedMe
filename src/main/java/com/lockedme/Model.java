package com.lockedme;

import java.util.*;


public class Model implements IModel {


	private Directory rootDirectory;

	public Model() {

		// filesAndDirectories.add(new File("testFile"));
	}

	@Override
	public void setRootDirectory(Directory rootdirectory) {
		this.rootDirectory = rootdirectory;

	}

	@Override
	public List<File> getFilesOfDirectory() {
		return rootDirectory.listAscendingFiles();
	}


	@Override
	public boolean addFileToDirectory(String filename) {
		String[] path = filename.split(java.io.File.separator);
		if(path.length == 1) {
			return rootDirectory.addFile(new File(path[0]));
		} else {
			return findFile(rootDirectory, Arrays.copyOfRange(path, 0, path.length - 1)).map(directSuperDir -> {
				if (directSuperDir.isDirectory()) {
					((Directory) directSuperDir).addFile(new File(path[path.length-1]));
					return true;
				} else {
					return false;
				}
			}).orElse(Boolean.FALSE);
		}
	}

	private static Optional<File> findFile(Directory directory, String[] path) {
		if (path.length == 0) {
			return Optional.empty();
		}
		if (path.length == 1) {
			return directory.listAscendingFiles().stream().filter(file -> file.getName().equals(path[0])).findFirst();
		} else {
			Optional<File> subdirectoryOpt = directory.listAscendingFiles().stream()
					.filter(file -> file.getName().equals(path[0]) && file.isDirectory()).findFirst();
			if (subdirectoryOpt.isPresent()) {
				return findFile((Directory) subdirectoryOpt.get(), Arrays.copyOfRange(path, 1, path.length));
			} else {
				return Optional.empty();
			}
		}
	}


	@Override
	public boolean addDirectoryToDirectory(String directoryName) {
		String[] path = directoryName.split(java.io.File.separator);
		if(path.length == 0) {
			return false;
		}
		if(path.length == 1) {
			rootDirectory.addDirectory(new Directory(path[0]));
			return true;
		} else {
			return findFile(rootDirectory, Arrays.copyOfRange(path, 0, path.length - 1)).map(directSuperDir -> {
				if (directSuperDir.isDirectory()) {
					((Directory) directSuperDir).addFile(new Directory(path[path.length-1]));
					return true;
				} else {
					return false;
				}
			}).orElse(Boolean.FALSE);
		}
	}

	@Override
	public boolean deleteFileFromDirectory(String filename) {
		String[] path = filename.split(java.io.File.separator);
		if(path.length == 1) {
			return rootDirectory.deleteFile(new File(filename));
		} else {
			return findFile(rootDirectory, Arrays.copyOfRange(path, 0, path.length - 1)).map(directSuperDir -> {
				if (directSuperDir.isDirectory()) {
					((Directory) directSuperDir).deleteFile(new Directory(path[path.length-1]));
					return true;
				} else {
					return false;
				}
			}).orElse(Boolean.FALSE);
		}
	}

	@Override
	public Directory getRootDirectory() {
		return this.rootDirectory;
	}

	@Override
	public List<List<String>> searchFileInDirectory(String filename) {
		ArrayList<String> pathToDirectoryToSearchIn = new ArrayList<String>();
		return searchFileInDirectory(rootDirectory, filename, pathToDirectoryToSearchIn);
	}

	private List<List<String>> searchFileInDirectory(Directory directoryToSearchIn, String fileName, final List<String> pathToDirectoryToSearchIn){
		List<List<String>> filesOut = new ArrayList<List<String>>();
		for(File file : directoryToSearchIn.listAscendingFiles()) {
			List<String> pathToThisFile = new ArrayList<>(List.copyOf(pathToDirectoryToSearchIn));
			pathToThisFile.add(file.getName());
			if(file.isDirectory()){
				filesOut.addAll(searchFileInDirectory((Directory) file,fileName,pathToThisFile));
			}
			if(file.getName().equals(fileName)) {
				filesOut.add(pathToThisFile);
			}
		}
		return filesOut;
	}

}
