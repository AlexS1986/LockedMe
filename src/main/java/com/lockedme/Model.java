package com.lockedme;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


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
		// return filesAndDirectories.stream().sorted((f1, f2) ->
		// f1.getName().compareTo(f2.getName())).collect(Collectors.toList());
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
		

		// return addFileToDirectory(rootDirectory, path);

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

	/*
	 * private boolean addFileToDirectory(Directory directory, String[] path) {
	 * return findFile(directory, Arrays.copyOfRange(path, 0, path.length-1)).map(
	 * directSuperDir -> { if (directSuperDir.isDirectory()) { ((Directory)
	 * directSuperDir).addFile(new File(path[path.length])); return true; } else {
	 * return false; } } ).orElse(Boolean.FALSE);
	 * 
	 * if(path.length == 1) { directory.addFile(new File(path[0])); // This should
	 * be a function parameter return true; } else { Optional<File> parentDirectory
	 * = findFile(directory, Arrays.copyOfRange(path, 0, path.length-1));
	 * if(parentDirectory.isPresent() && parentDirectory.get().isDirectory()) {
	 * ((Directory) parentDirectory.get()).addFile(new File(path[path.length-1]));
	 * return true; } else { return false; }
	 * 
	 * 
	 * Optional<File> subdirectoryOpt = directory.listFiles().stream().filter(file
	 * -> file.getName().equals(path[0]) && file.isDirectory()).findFirst(); if
	 * (subdirectoryOpt.isPresent()) { return addFileToDirectory((Directory)
	 * subdirectoryOpt.get(), Arrays.copyOfRange(path, 1, path.length)); } else {
	 * return false; }
	 * 
	 * //}
	 * 
	 * //}
	 * 
	 * 
	 * 
	 * 
	 * private boolean perFormActionIfFound(Directory directory, String[]
	 * path,Function<File, Boolean> action,String parameter) { return
	 * findFile(directory, Arrays.copyOfRange(path, 0, path.length)).map( file ->
	 * action.apply(file)).orElse(Boolean.FALSE);
	 * 
	 * 
	 * if(path.length == 1) { directory.addFile(new File(path[0])); // This should
	 * be a function parameter return true; } else { Optional<File> parentDirectory
	 * = findFile(directory, Arrays.copyOfRange(path, 0, path.length-1));
	 * if(parentDirectory.isPresent() && parentDirectory.get().isDirectory()) {
	 * ((Directory) parentDirectory.get()).addFile(new File(path[path.length-1]));
	 * return true; } else { return false; }
	 * 
	 * 
	 * Optional<File> subdirectoryOpt = directory.listFiles().stream().filter(file
	 * -> file.getName().equals(path[0]) && file.isDirectory()).findFirst(); if
	 * (subdirectoryOpt.isPresent()) { return addFileToDirectory((Directory)
	 * subdirectoryOpt.get(), Arrays.copyOfRange(path, 1, path.length)); } else {
	 * return false; }
	 * 
	 * //}
	 * 
	 * }
	 */

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

	/*
	 * private boolean addDirectoryToDirectory(Directory directory, String[] path) {
	 * if (path.length == 1) { directory.addDirectory(new Directory(path[0]));
	 * return true; } else { Optional<File> subdirectoryOpt =
	 * directory.listFiles().stream() .filter(file -> file.getName() == path[0] &&
	 * file.isDirectory()).findFirst(); if (subdirectoryOpt.isPresent()) { return
	 * addDirectoryToDirectory((Directory) subdirectoryOpt.get(),
	 * Arrays.copyOfRange(path, 1, path.length)); } else { return false; } }
	 * 
	 * }
	 */

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
	public boolean searchFileInDirectory(String filename) {
		String[] path = filename.split(java.io.File.separator);
		if(path.length == 1) {
			return rootDirectory.listAscendingFiles().contains(new File(filename));
		} else {
			return findFile(rootDirectory, Arrays.copyOfRange(path, 0, path.length)).map( file -> true ).orElse(Boolean.FALSE);
		}
		
	}

}
