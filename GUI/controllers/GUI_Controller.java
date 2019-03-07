package controllers;

import java.util.HashMap;

import application.View;
import javafx.scene.layout.Pane;

public class GUI_Controller {
	
	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	
	public GUI_Controller(View view) {
		this.view = view;
	}
	
	public boolean handleLogin(String username, String password) {
		//TODO: Check if username and password exist in database
		boolean userExists = true;
		if (userExists) {
			return false;
		} else {
			//Kommentoi nämä, jos ei ole lokaalia tietokantaa
			//Kayttaja kayttaja = new Kayttaja(username, password);
			//kayttajaDAO.createKayttaja(kayttaja);
			return true;
		}
	}
	
	public boolean handleRegister(String username, String password) {
		//TODO: Check if username exists
		boolean nameTaken = true;
		if (nameTaken) {
			return false;
		} else {
			//TODO: Create a new user
			
			return true;
		}
	}
	
	public void addScreen(String name, Pane screen) {
		screens.put(name, screen);
	}
	
	public void activateScreen(String name) {
		view.setRoot(screens.get(name));
	}

}
