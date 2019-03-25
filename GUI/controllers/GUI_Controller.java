package controllers;

import java.util.HashMap;

import application.View;
import database.ConnectionHandler;
import database.Kayttaja;
import database.KayttajaAccessObject;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserAuth;

public class GUI_Controller {

	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	// private static KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
	private ConnectionHandler connHandler;
	private Kayttaja user;

	public GUI_Controller(View view) {
		this.view = view;
		// TODO: Move this call to a more proper place.
		connHandler = ConnectionHandler.getInstance();
		connHandler.openTunnel();
		uAuth = new UserAuth();
	}

	/**
	 * Checks the database for the given username and checks if the combination
	 * matches.<br>
	 * 
	 * @param username
	 * @param password
	 * @return the state of the login process
	 */
	public boolean handleLogin(String username, String password) {
		user = uAuth.login(username, password);
		if (user != null) {
			// Kommentoi nämä, jos ei ole lokaalia tietokantaa
			/*
			KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
			Kayttaja kayttaja = new Kayttaja(username, password);
			kayttajaDAO.createKayttaja(kayttaja);*/
			return true;
		}
		return false;
	}

	/**
	 * Registers a new user based on the given username and password if the username
	 * has not already been taken.<br>
	 * 
	 * @param username
	 * @param password
	 * @return the state of the registration
	 */
	public boolean handleRegister(String username, String password) {
		user = uAuth.register(username, password);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}
	
	//TODO: Complete method
	public void handleLogout() {
		user = null;
	}

	//TODO: Complete method
	public void handleExit(Stage stage) {
		handleLogout();
		stage.close();
	}
	
	/**
	 * Gets the username of a chosen user
	 * 
	 * @return the username of a certain user ID
	 */
	public String getUserName() {
		return user.getName();
	}

	/**
	 * Adds a screen with the given parameters to teh mapping
	 * 
	 * @param name
	 * @param screen
	 */
	public void addScreen(String name, Pane screen) {
		screens.put(name, screen);
	}

	/**
	 * Activates a screen with the given name in the mainscreen
	 * 
	 * @param name
	 */
	public void activateScreen(String name) {
		view.setRoot(screens.get(name));
	}
}
