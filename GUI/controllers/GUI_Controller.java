package controllers;

import java.util.HashMap;

import javax.swing.JOptionPane;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import application.View;
import database.ConnectionHandler;
import database.Kayttaja;
import database.KayttajaAccessObject;
import javafx.scene.layout.Pane;
import models.UserAuth;

public class GUI_Controller {
	
	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	//private static KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
	private ConnectionHandler connHandler;
	
	
	public GUI_Controller(View view) {
		this.view = view;
		uAuth = new UserAuth();
		connHandler = ConnectionHandler.getInstance();
		connHandler.openTunnel();
	}
	
	/**
	 * Checks the database for the given username and checks if the combination matches.<br>
	 * 
	 * @param username
	 * @param password
	 * @return the state of the login process
	 */
	public boolean handleLogin(String username, String password) {	
		boolean loginSuccessful = uAuth.login(username, password);
		if (loginSuccessful) {
			//Kommentoi nämä, jos ei ole lokaalia tietokantaa
			KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
			Kayttaja kayttaja = new Kayttaja(username, password);
			kayttajaDAO.createKayttaja(kayttaja);
			return true;
		}
		return false;
	}
	
	/**
	 * Registers a new user based on the given username and password if the username has not already been taken.<br>
	 * 
	 * @param username
	 * @param password
	 * @return the state of the registration
	 */
	public boolean handleRegister(String username, String password) {
		boolean regSuccessful = uAuth.register(username, password);
		if (regSuccessful) {
			//TODO: Create a new user
			
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the username of a chosen user
	 * 
	 * @return the username of a certain user ID
	 */
	public String getUserName() {
		return "User";
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
