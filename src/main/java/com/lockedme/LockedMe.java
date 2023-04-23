package com.lockedme;

import java.util.Scanner;

public class LockedMe {

	public static void main(String[] args) {
		IModel model = new Model();
		IView view = new View(System.out);
		Scanner scanner = new Scanner(System.in);
		Directory rootDirectory = LockedMeController.getRootDirectory(scanner,view);
		run(scanner, model, view, rootDirectory);
	}
	
	private static void run(Scanner scanner, IModel model, IView view, Directory rootDirectory) {
		
		try {
			LockedMeController controller = new LockedMeController(scanner,model,view, rootDirectory);
		} catch (LockedMeControllerException e){
			System.out.println("Invalid input." + e.getMessage());
			run(scanner, model,view, rootDirectory);
		} finally {
			scanner.close();
		}	
		
	}
}
