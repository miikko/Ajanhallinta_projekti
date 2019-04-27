package controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import application.ScreenFactory;
import application.View;
import database.DatabaseHandler;
import database.Kayttaja;
import database.Sitting;
import database.UserGroup;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserAuth;
import service.Recorder;

public class GUI_Controller {

	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	private StringProperty usernameProperty = new SimpleStringProperty();
	private Kayttaja user;
	private Recorder rec;
	private DatabaseHandler dbHandler;

	public GUI_Controller(View view) {
		this.view = view;
		dbHandler = new DatabaseHandler();
		uAuth = new UserAuth(dbHandler);
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
		if (user == null) {
			return false;
		}
		usernameProperty.set(user.getName()); 
		screens.put("Main", ScreenFactory.createScreen("Main", 10, this));
		activateScreen("Main");
		return true;
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
		if (user == null) {
			return false;
		}
		usernameProperty.set(user.getName());
		screens.put("Main", ScreenFactory.createScreen("Main", 10, this));
		view.createAndDisplayPopup("Registration successful!", "Main");
		return true;
	}

	public boolean handleUsernameChange(String username) {
		Kayttaja tempUser = uAuth.changeUsername(user.getId(), username, user.getPassword());
		if (tempUser == null) {
			System.out.println("Nimen vaihto ei onnistunut");
			return false;
		} else {
			System.out.println("Nimen vaihto onnistui!");
			user = tempUser;
			usernameProperty.set(user.getName());
			return true;
		}
	}

	public boolean handlePasswordChange(String pass1, String pass2) {
		Kayttaja tempUser = uAuth.changePassword(user.getId(), user.getName(), pass1, pass2);
		if (tempUser == null) {
			System.out.println("Salasanan vaihto epäonnistui");
			return false;
		} else {
			System.out.println("Salasanan vaihto onnistui");
			user = tempUser;
			return true;
		}

	}

	public boolean startRecording() {
		if ((rec != null && rec.isAlive()) || user == null) {
			return false;
		}
		rec = new Recorder(user, dbHandler);
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
	
	//TODO: Complete method
	public Set<Sitting> getSittings(LocalDate sDate, LocalDate eDate) {
		if (user == null) {
			return null;
		}
		Date startDate = Date.from(sDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		Date endDate = Date.from(eDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		return dbHandler.fetchSittings(startDate, endDate, user.getId());
	}
	
	public List<UserGroup> getUserGroups() {
		if (user == null) {
			return null;
		}
		return dbHandler.fetchUserGroups(user.getId());
	}

	// TODO: Complete method, BUG: recorder doesn't stop instantly, instead stops on
	// next timer check
	public void handleLogout() {
		stopRecording();
		user = null;
	}

	// TODO: Complete method
	public void handleExit() {
		handleLogout();
		Stage stage = (Stage) view.getScene().getWindow();
		stage.close();
	}

	public StringProperty getUsernameProperty() {
		return usernameProperty;
	}
	
	public Integer getUserId() {
		if (user == null) {
			return null;
		}
		return user.getId();
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
