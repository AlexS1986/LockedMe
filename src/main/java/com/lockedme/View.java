package com.lockedme;

import java.io.PrintStream;
import java.util.List;

public class View implements IView {

	private abstract class Screen {
		abstract protected String showTitle();

		abstract protected String showOptions();

		abstract protected String showContent();

		protected String showExitOption() {
			return "Enter " + IView.COMMANDEXIT + " and confirm by pressing ENTER to return to the previous menu."
					+ System.lineSeparator();
		}

		String show() {
			return showTitle() + showOptions() + showContent() + showExitOption();
		}

		protected String showSuccess() {
			return "Operation successful";
		};

		protected String showFailure() {
			return "Operation failed";
		};
	}

	private class PreWelcomeScreen extends Screen {

		@Override
		protected String showTitle() {
			return showProgramName() + System.lineSeparator();
		}

		@Override
		protected String showOptions() {
			return "Please enter the full path to the root directory. Default is the current working directory. Confirm with Enter."
					+ System.lineSeparator();
		}

		@Override
		protected String showContent() {
			return "" + System.lineSeparator();
		}

		protected String showExitOption() {
			return "Enter " + IView.COMMANDEXIT + " and confirm with Enter to return to close the application."
					+ System.lineSeparator();
		}
	}

	class WelcomeScreen extends Screen {
		@Override
		protected String showTitle() {
			String welcomeScreen = "Welcome to " + showProgramName() +  System.lineSeparator() + "Information about the author."+ System.lineSeparator() + "Author Name: " + showAuthor() + "Email: "
					+ showAuthorEmail() + System.lineSeparator()+  "Root Directory: " + model.getRootDirectory().getName()
					+ System.lineSeparator() + System.lineSeparator();
			return welcomeScreen;
		}

		@Override
		protected String showOptions() {
			String options = "You have the following options:" + System.lineSeparator() + "1. List all files in "
					+ model.getRootDirectory().getName() + " in ascending order: " + COMMANDLIST
					+ System.lineSeparator() + "2. Add a user specified file to " + model.getRootDirectory().getName()
					+ ": " + COMMANDADD + System.lineSeparator() + "3. Delete a user specified file from "
					+ model.getRootDirectory().getName() + ": " + COMMANDDELETE + System.lineSeparator()
					+ "4. Search a user specified file from " + model.getRootDirectory().getName() + ": "
					+ COMMANDSEARCH + System.lineSeparator() +  System.lineSeparator() + "In order to proceed, enter the desired command " + COMMANDLIST + ", " + COMMANDADD
					+ ", " + COMMANDDELETE + " or " + COMMANDSEARCH + " and press Enter." + System.lineSeparator();
			return options;
		}

		@Override
		protected String showExitOption() {
			return "Enter " + IView.COMMANDEXIT + " and confirm with Enter to close the application."
					+ System.lineSeparator();
		}

		@Override
		protected String showContent() {
			return "" + System.lineSeparator();
		}
	}

	private class AddScreen extends Screen {

		@Override
		protected String showTitle() {
			return "This is the Add Screen. ";
		}

		@Override
		protected String showOptions() {
			return "Enter a command of the format F|D <path> to create a file (F) or a directory (D)." + System.lineSeparator()
					+ "The <path> is the relative path of the file compared to "
					+ model.getRootDirectory().getName() +  System.lineSeparator() +  "Confirm with Enter." + System.lineSeparator() + System.lineSeparator() + 
					"Example: D d1   (adds directory d1 to " + model.getRootDirectory().getName() + ")" + System.lineSeparator() +
					"Example: F d1/f1 (adds file f1 to " + model.getRootDirectory().getName() + "/d1)" + System.lineSeparator();
		}

		@Override
		protected String showContent() {
			return "" + System.lineSeparator();
		}
		
		protected String showSuccess() {
			return "File added.";
		};

		protected String showFailure() {
			return "File could not be added.";
		};
	}

	private class DeleteScreen extends Screen {

		@Override
		protected String showTitle() {
			return "This is the Delete Screen. ";
		}

		@Override
		protected String showOptions() {
			return "Enter the relative path of the file compared to " + model.getRootDirectory().getName()
					+ "." + System.lineSeparator() + "The <path> is the relative path of the file compared to " + model.getRootDirectory().getName()
					+ ". Confirm with Enter." + System.lineSeparator()+
					"Example: d1    (deletes directory d1 from " + model.getRootDirectory().getName() + ")" + System.lineSeparator() +
					"Example: d1/f1 (deletes file f1 from " + model.getRootDirectory().getName() + "/d1 )" + System.lineSeparator();
		}

		@Override
		protected String showContent() {
			return "" + System.lineSeparator();
		}
		
		protected String showSuccess() {
			return "File deleted.";
		};

		protected String showFailure() {
			return "File could not be deleted.";
		};

	}

	private class SearchScreen extends Screen {

		@Override
		protected String showTitle() {
			return "This is the Search Screen. " + System.lineSeparator();
		}

		@Override
		protected String showOptions() {
			return "Enter the relative path of the file compared to " + model.getRootDirectory().getName()  +"." + System.lineSeparator()
					+ "The <path> is the relative path of the file compared to " + model.getRootDirectory().getName()
					+ ". Confirm with Enter." + System.lineSeparator() + 
					"Example: d1    (searches directory d1 in " + model.getRootDirectory().getName() + ")" + System.lineSeparator() +
					"Example: d1/f1 (searches file f1 in " + model.getRootDirectory().getName() + "/d1 )" + System.lineSeparator();
		}

		@Override
		protected String showContent() {
			return "" + System.lineSeparator();
		}
		
		protected String showSuccess() {
			return "File found.";
		};

		protected String showFailure() {
			return "File could not be found.";
		};

	}

	private class ListScreen extends Screen {

		@Override
		protected String showTitle() {
			return "This is the List Screen" + System.lineSeparator();
		}

		@Override
		protected String showOptions() {
			return "" + System.lineSeparator();
		}

		@Override
		protected String showContent() {
			String output =  showListOfFilesInDirectory(model.getFilesOfDirectory(), 0);
			if (output.isEmpty()) {
				output = "is empty";
			}
			output = "Files in " + model.getRootDirectory().getName()+ ":" + System.lineSeparator() + output;
			return output + System.lineSeparator() +  System.lineSeparator();
		}

		private String showListOfFilesInDirectory(List<File> files, int indentationSpaces) {
			String listOfAllFilesInDirectory = "";
			for (File file : files) {
				listOfAllFilesInDirectory = listOfAllFilesInDirectory + "-".repeat(indentationSpaces) + showFile(file)
						+ System.lineSeparator();
				if (file.isDirectory()) {
					listOfAllFilesInDirectory = listOfAllFilesInDirectory
							+ showListOfFilesInDirectory(((Directory) file).listAscendingFiles(), indentationSpaces + 1);
				}
			}
			return listOfAllFilesInDirectory;
		}

		private String showFile(File file) {
			return file.isDirectory() ? "+" + file.getName() : "-" + file.getName(); 
		}

	}

	private IModel model;
	private PrintStream stream;

	public View(PrintStream stream) {
		this.stream = stream;
	}

	@Override
	public String showPreWelcomeScreen() {
		return new PreWelcomeScreen().show();
	}

	@Override
	public String showWelcomeScreen() {
		return new WelcomeScreen().show();
	}

	@Override
	public String showAddScreen() {
		return new AddScreen().show();
	}

	@Override
	public String showDeleteScreen() {
		return new DeleteScreen().show();
	}

	@Override
	public String showSearchScreen() {
		return new SearchScreen().show();
	}

	private String showProgramName() {
		return IView.PROGRAMNAME + System.lineSeparator();
	}

	private String showAuthor() {
		return IView.AUTHORNAME + System.lineSeparator();
	}

	private String showAuthorEmail() {
		return IView.AUTHOREMAIL + System.lineSeparator();
	}

	@Override
	public String showListScreen() {
		return new ListScreen().show();
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;

	}

	@Override
	public String showGeneralErrorMessage() {
		String generalErrorMessage = "An error occurred during execution of LockedMe";
		System.out.println(generalErrorMessage);
		return generalErrorMessage;
	}

	@Override
	public void outputToUI(String message) {
		stream.println(message);

	}

	@Override
	public String showExitMessage() {
		return "Application exited successfully.";
	}

	@Override
	public String showAddScreenSuccess() {
		return new AddScreen().showSuccess();
	}

	@Override
	public String showAddScreenFailure() {
		return new AddScreen().showFailure();
	}

	@Override
	public String showDeleteScreenSuccess() {
		return new DeleteScreen().showSuccess();
	}

	@Override
	public String showDeleteScreenFailure() {
		return new DeleteScreen().showFailure();
	}

	@Override
	public String showSearchScreenSuccess() {
		return new SearchScreen().showSuccess();
	}

	@Override
	public String showSearchScreenFailure() {
		return new SearchScreen().showFailure();
	}

	@Override
	public String showWelcomeScreenFailure() {
		return new WelcomeScreen().showOptions();
	}

	@Override
	public String showListScreenSuccess() {
		return new ListScreen().showSuccess();
	}

	@Override
	public String showListScreenFailure() {
		return new ListScreen().showFailure();
	}

}
