package com.lockedme;

import java.util.Scanner;

public class LockedMeController {
	private Scanner scanner;
	private IModel model;
	private IView view;

	public LockedMeController(Scanner scanner, IModel model, IView view, Directory rootDirectory) throws LockedMeControllerException {
		this.scanner = scanner;
		this.model = model;
		model.setRootDirectory(rootDirectory);
		this.view = view;
		view.setModel(model);
		runWelcomeScreen(this.scanner);
	}

	public static Directory getRootDirectory( Scanner scanner, IView view)  {
		Directory rootDirectory = null;
		try {
			view.outputToUI(view.showPreWelcomeScreen());
			String command = scanner.nextLine().trim();
			while(!command.equals(IView.COMMANDEXIT)) {
				if(command.isEmpty()) {
					command = System.getProperty("user.dir");
				}
				rootDirectory = new Directory(command);	
				break;
			}
		}  catch(Throwable e) {
			rootDirectory = new Directory("");
		} 
		return rootDirectory;
	}

	private void runWelcomeScreen(Scanner scanner) throws LockedMeControllerException {
		try {
			view.outputToUI(view.showWelcomeScreen());
			String command = scanner.nextLine().trim();
			while (!command.equals(IView.COMMANDEXIT)) {
				switch (command) {
				case IView.COMMANDLIST:
					runListScreen(scanner);
					break;
				case IView.COMMANDDELETE:
					runDeleteScreen(scanner);
					break;
				case IView.COMMANDSEARCH:
					runSearchScreen(scanner);
					break;
				case IView.COMMANDADD:
					runAddScreen(scanner);
					break; 
				default:
					view.outputToUI(view.showWelcomeScreenFailure());
				}
				command = scanner.nextLine().trim();
			}
			runExit(scanner);
		} catch (Throwable  e) {
			throw new LockedMeControllerException("Welcome screen unexpected error:" + e.getMessage());
		} 
	}

	private void runListScreen(Scanner scanner) throws LockedMeControllerException {
		try {
			view.outputToUI(view.showListScreen());
			String command = scanner.nextLine().trim();
			while (!command.equals(IView.COMMANDEXIT)) {
				view.outputToUI(view.showListScreen());
				command = scanner.nextLine().trim();
			}
			runWelcomeScreen(scanner);
		} catch (Throwable  e) {
			throw new LockedMeControllerException("List screen unexpected error:" + e.getMessage());
		}
		
	}

	private void runDeleteScreen(Scanner scanner) throws LockedMeControllerException {
		try {
			view.outputToUI(view.showDeleteScreen());
			String command = scanner.nextLine().trim();
			String output = null;
			while (!command.equals(IView.COMMANDEXIT)) {
				if (model.deleteFileFromDirectory(command)) {
					output = view.showDeleteScreenSuccess();
				} else {
					output = view.showDeleteScreenFailure();
				}
				view.outputToUI(output);
				command = scanner.nextLine().trim();
			}
			runWelcomeScreen(scanner);
		} catch(Throwable  e) {
			throw new LockedMeControllerException("Delete screen unexpected error:" + e.getMessage());
		}
		
	
	}
	
	private void runSearchScreen(Scanner scanner) throws LockedMeControllerException {
		try {
			view.outputToUI(view.showSearchScreen());
			String command = scanner.nextLine().trim();
			while (!command.equals(IView.COMMANDEXIT)) {
				String output = null;
				if(model.searchFileInDirectory(command)) {
					output  = view.showSearchScreenSuccess();
				} else {
					output = view.showSearchScreenFailure();
				}
				view.outputToUI(output);
				command = scanner.nextLine().trim();
			}
			runWelcomeScreen(scanner);
		} catch (Throwable  e) {
			throw new LockedMeControllerException("Search screen unexpected error:" + e.getMessage());
		}
		
	}
	

	private void runAddScreen(Scanner scanner) throws LockedMeControllerException {
		try {
			view.outputToUI(view.showAddScreen());
			String command = scanner.nextLine().trim();
			while (!command.equals(IView.COMMANDEXIT)) {
				String[] commandSplitted = command.split(" ");
				if(commandSplitted.length != 2 ) {
					view.outputToUI(view.showAddScreen());
				}
				String output = null;
				switch(commandSplitted[0]) {
				case "F":
					output = model.addFileToDirectory(commandSplitted[1]) ? view.showAddScreenSuccess() : view.showAddScreenFailure();
					break;
				case "D":
					output = model.addDirectoryToDirectory(commandSplitted[1]) ? view.showAddScreenSuccess() : view.showAddScreenFailure();
					break;
				default:
					output = view.showAddScreenFailure();
				}
				view.outputToUI(output);
				command = scanner.nextLine().trim();
			}
			runWelcomeScreen(scanner);
		} catch(Throwable  e) {
			throw new LockedMeControllerException("Add screen unexpected error:" + e.getMessage());
		}
		
	}
	
	private void runExit(Scanner canner) {
		try {
			scanner.close();
			view.outputToUI(view.showExitMessage());
		} finally {
			System.exit(0);
		}
	}

}
