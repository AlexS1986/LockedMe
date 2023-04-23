package com.lockedme;


public interface IView {
	final String mainDirectoryName = "main directory";
	final String COMMANDLIST = "LIST";
	final String COMMANDDELETE = "DELETE";
	final String COMMANDADD = "ADD";
	final String COMMANDEXIT = "EXIT";
	final String COMMANDSEARCH = "SEARCH";
	final String PROGRAMNAME = "<<< LockedMe >>>";
	final String AUTHORNAME = "Alexander Schlueter";
	final String AUTHOREMAIL = "alexander.schlueter@gx.de";
	
	String showWelcomeScreenFailure();
	
	String showPreWelcomeScreen();
	String showAddScreen();
	String showDeleteScreen();
	String showListScreen();
	String showListScreenSuccess();
	String showListScreenFailure();
	
	
	String showSearchScreen();
	void setModel(IModel model);
	String showGeneralErrorMessage();
	void outputToUI(String message);
	String showExitMessage();
	
	String showAddScreenSuccess();
	String showAddScreenFailure();
	
	String showDeleteScreenSuccess();
	String showDeleteScreenFailure();
	
	String showSearchScreenSuccess();
	String showSearchScreenFailure();
	String showWelcomeScreen();
	


}
