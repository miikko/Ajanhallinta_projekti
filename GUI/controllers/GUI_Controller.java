package controllers;

import java.util.HashMap;

import application.View;
import database.ConnectionHandler;
import database.Kayttaja;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserAuth;
import service.Recorder;

public class GUI_Controller {

	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	// private static KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
	private ConnectionHandler connHandler;
	private Kayttaja user;
	private Recorder rec;

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
	
	public boolean handleUserUsernameChanges(String username) {
		Kayttaja tempUser = uAuth.changeUserUsername(user.getId(), username, user.getPassword());
		if(tempUser == null) {
			System.out.println("Nimen vaihto ei onnistunut");
			return false;
		}else {
			System.out.println("Nimen vaihto onnistui!");
			user = tempUser;
			return true;
		}
	}
	
	public boolean handleUserPasswordChanges(String pass1, String pass2) {
		Kayttaja tempUser = uAuth.changeUserPassword(user.getId(), user.getName(), pass1, pass2);
			if(tempUser == null) {
				System.out.println("Salasanan vaihto epäonnistui");
				return false;
			}else {
				System.out.println("Salasanan vaihto onnistui");
				user = tempUser;
				return true;
			}
		
	}
	
	public boolean startRecording() {
		if ((rec != null && rec.isAlive()) || user == null) {
			return false;
		}
		rec = new Recorder(user);
		rec.start();
		return true;
	}
	
	public boolean stopRecording() {
		if (rec == null || !rec.isAlive()) {
			return false;
		}
		rec.quit();
		return true;
	}
	
	//TODO: Complete method, BUG: recorder doesn't stop instantly, instead stops on next timer check
	public void handleLogout() {
		stopRecording();
		user = null;
	}

	//TODO: Complete method
	public void handleExit(Stage stage) {
		handleLogout();
		stage.close();
	}
	
	public String getUserName() {
		return user.getName();
	}

	/**
	 * Adds a screen with the given parameters to the mapping
	 * 
	 * @param name
	 * @param screen
	 */
	public void addScreen(String name, Pane screen) {
		screens.put(name, screen);
	}

	/**
	 * Activates a screen with the given name
	 * 
	 * @param name
	 */
	public void activateScreen(String name) {
		view.setRoot(screens.get(name));
	}
}
