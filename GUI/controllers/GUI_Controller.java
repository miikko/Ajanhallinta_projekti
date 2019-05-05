package controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import application.ScreenFactory;
import application.View;
import database.DatabaseHandler;
import database.Kayttaja;
import database.Restriction;
import database.Sitting;
import database.UserGroup;
import database.WindowTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserAuth;
import service.Recorder;

/**
 * Controller class for most GUI-package classes.<br>
 * Contains handling methods for login, user info changing, recording and database calls from GUI-package classes.<br>
 * Also stores the logged in user as a Kayttaja-object. 
 * @author miikk & Arttuhal
 *
 */
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
	
	public Set<String> getAllProgramNames() {
		Set<String> progNames = new HashSet<>();
		Set<Sitting> sittings = getSittings(LocalDate.now().minusYears(100), LocalDate.now());
		for (Sitting sitting : sittings) {
			Set<WindowTime> wts = sitting.getWindowTimes();
			for (WindowTime wt : wts) {
				progNames.add(wt.getProgramName());
			}
		}
		return progNames;
	}
	
	public Set<Sitting> getSittings(LocalDate sDate, LocalDate eDate) {
		if (user == null) {
			return null;
		}
		// Commented lines based on the Date variable and replaced with LocalDateTime, kept in case of undiscovered bugs
//		LocalDateTime startDate = LocalDateTime.from(sDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//		LocalDateTime endDate = LocalDateTime.from(eDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDateTime startDate = LocalDateTime.of(sDate, LocalTime.MIN);
		LocalDateTime endDate = LocalDateTime.of(eDate.plusDays(1), LocalTime.MIN);
		return dbHandler.fetchSittings(startDate, endDate, user.getId());
	}
	
	public Set<Sitting> getGroupSittings(LocalDate sDate, LocalDate eDate, UserGroup group) {
		if (group == null) {
			return null;
		}
		Set<Sitting> sittings = new HashSet<>();
//		LocalDateTime startDate = LocalDateTime.from(sDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
//		LocalDateTime endDate = LocalDateTime.from(eDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDateTime startDate = LocalDateTime.of(sDate, LocalTime.MIN);
		LocalDateTime endDate = LocalDateTime.of(eDate.plusDays(1), LocalTime.MIN);
		for (int userId : group.getUserIds()) {
			sittings.addAll(dbHandler.fetchSittings(startDate, endDate, userId));
		}
		return sittings;
	}
	
	public List<Restriction> getRestrictions() {
		if (user == null) {
			return null;
		}
		return dbHandler.fetchRestrictions(user.getId());
	}
	
	public boolean saveRestriction(HashMap<String, Integer[]> restrictionDaySettings, String progName) {
		if (user == null || progName == null) {
			return false;
		}
		for (String weekday : restrictionDaySettings.keySet()) {
			Integer[] time = restrictionDaySettings.get(weekday);
			int hours = time[0];
			int minutes = time[1];
			dbHandler.sendRestriction(new Restriction(user, progName, weekday, hours, minutes));
		}
		return true;
	}
	
	public boolean updateRestriction(Restriction restriction) {
		if (user == null || restriction == null) {
			return false;
		}
		return dbHandler.updateRestriction(restriction);
	}
	
	public boolean removeRestriction(Restriction restriction) {
		if (restriction == null) {
			return false;
		}
		return dbHandler.deleteRestriction(restriction);
	}
	
	public List<UserGroup> getUserGroups() {
		if (user == null) {
			return null;
		}
		return dbHandler.fetchUserGroups(user.getId());
	}

	public boolean addUserToGroup(UserGroup userGroup, String userIdStr) {
		int userId = 0;
		try {
			userId = Integer.parseInt(userIdStr);
		} catch (NumberFormatException e) {
			return false;
		}
		if (user.getId() == userId || userGroup.getUserIds().contains(userId) || dbHandler.fetchUser(userId) == null) {
			return false;
		}
		userGroup.addUserId(userId);
		return true;
	}
	
	public boolean removeUserFromGroup(UserGroup group, String userIdStr) {
		int userId = 0;
		try {
			userId = Integer.parseInt(userIdStr);
		} catch (NumberFormatException e) {
			return false;
		}
		if (user.getId() == userId) {
			return false;
		}
		group.removeUserId(userId);
		return true;
	}
	
	public boolean saveUserGroup(UserGroup userGroup) {
		String groupName = userGroup.getGroupName();
		if (userGroup.getUserIds().size() == 0 || groupName == null || groupName.equals("")) {
			return false;
		}
		List<UserGroup> userGroups = dbHandler.fetchUserGroups(user.getId());
		for (UserGroup group : userGroups) {
			if (group.getGroupName().equals(groupName)) {
				return false;
			}
		}
		return dbHandler.sendUserGroup(userGroup);
	}
	
	public boolean removeUserGroup(UserGroup userGroup) {
		if (userGroup == null) {
			return false;
		}
		return dbHandler.deleteGroup(userGroup);
	}
	
	// TODO: Complete method, BUG: recorder doesn't stop instantly, instead stops on
	// next timer check
	public void handleLogout() {
		stopRecording();
		user = null;
	}

	
	public void handleExit() {
		handleLogout();
		Stage stage = (Stage) view.getScene().getWindow();
		stage.close();
	}

	public StringProperty getUsernameProperty() {
		return usernameProperty;
	}
	
	/**
	 * @return the current user's id, or null if no user is logged in.
	 */
	public Integer getUserId() {
		if (user == null) {
			return null;
		}
		return user.getId();
	}

	/**
	 * Adds a screen with the given parameters to the screen-mapping.
	 * 
	 * @param name  String that works as a key in the screen-mapping.
	 * @param screen The Pane to be stored.
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
