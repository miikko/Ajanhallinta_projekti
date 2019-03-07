package controllers;

import java.util.HashMap;

import application.View;
import javafx.scene.layout.Pane;
import models.UserAuth;

public class GUI_Controller {
	
	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	
	public GUI_Controller(View view) {
		this.view = view;
		uAuth = new UserAuth();
	}
	
	public boolean handleLogin(String username, String password) {
		return true;
		/*
		boolean loginSuccessful = uAuth.login(username, password);
		if (loginSuccessful) {
			//Kommentoi nämä, jos ei ole lokaalia tietokantaa
			//Kayttaja kayttaja = new Kayttaja(username, password);
			//kayttajaDAO.createKayttaja(kayttaja);
			return true;
		} else {
			return false;
		}*/
	}
	
	public boolean handleRegister(String username, String password) {
		boolean regSuccessful = uAuth.register(username, password);
		if (regSuccessful) {
			//TODO: Create a new user
			
			return true;
		} else {
			return false;
		}
	}
	
	public String getUserName() {
		return "User";
	}
	
	public void addScreen(String name, Pane screen) {
		screens.put(name, screen);
	}
	
	public void activateScreen(String name) {
		view.setRoot(screens.get(name));
	}

}
