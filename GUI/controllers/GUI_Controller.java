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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.UserAuth;
import service.Recorder;

/**
 * Controller class for most GUI-package classes.<br>
 * Contains handling methods for login, user info changing, recording and
 * database calls from GUI-package classes.<br>
 * Also stores the logged in user as a Kayttaja-object.
 * 
 * @author miikk & Arttuhal
 *
 */
public class GUI_Controller {

	private View view;
	private HashMap<String, Pane> screens = new HashMap<>();
	private UserAuth uAuth;
	private Kayttaja user;
	private Recorder rec;
	private DatabaseHandler dbHandler;

	public GUI_Controller(View view) {
		this.view = view;
		dbHandler = DatabaseHandler.getInstance();
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
		screens.put("Main", ScreenFactory.createScreen("Main", 10, this));
		view.createAndDisplayPopup("Registration successful!", "Main");
		return true;
	}

	/**
	 * Changes the user's username if the new name is not taken or empty.
	 * 
	 * @param username
	 * @return the state of the username change
	 */
	public boolean handleUsernameChange(String username) {
		Kayttaja tempUser = uAuth.changeUsername(user.getId(), username, user.getPassword());
		if (tempUser == null) {
			return false;
		} else {
			user = tempUser;
			return true;
		}
	}

	/**
	 * Changes the user's password if the password is not empty and the two
	 * passwords match
	 * 
	 * @param pass1
	 * @param pass2
	 * @return the state of the password change
	 */
	public boolean handlePasswordChange(String pass1, String pass2) {
		Kayttaja tempUser = uAuth.changePassword(user.getId(), user.getName(), pass1, pass2);
		if (tempUser == null) {
			return false;
		} else {
			user = tempUser;
			return true;
		}

	}

	/**
	 * Creates and starts a new recorder
	 * 
	 * @return false if a recorder is already running or if the user is not logged
	 *         in, true otherwise.
	 */
	public boolean startRecording() {
		if ((rec != null && rec.isAlive()) || user == null) {
			return false;
		}
		rec = new Recorder(user, dbHandler);
		rec.start();
		return true;
	}

	/**
	 * Stops the current recorder
	 * 
	 * @return false if the recorder is null or if the recorder was already closed,
	 *         true otherwise.
	 */
	public boolean stopRecording() {
		if (rec == null || !rec.isAlive()) {
			return false;
		}
		rec.quit();
		return true;
	}

	/**
	 * Loads all the WindowTimes belonging to the current user and adds each unique
	 * program name to the to be returned Set.
	 * 
	 * @return a Set of unique encountered program names.
	 */
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

	/**
	 * Gets all the Sittings that are in the given Date range and belong to the
	 * current user from the database.
	 * 
	 * @param sDate the first date at which Sittings are considered
	 * @param eDate the last date at which Sittings are considered
	 * @return all the Sittings belonging to current user in the given Date range.
	 */
	public Set<Sitting> getSittings(LocalDate sDate, LocalDate eDate) {
		if (user == null) {
			return null;
		}
		LocalDateTime startDate = LocalDateTime.of(sDate, LocalTime.MIN);
		LocalDateTime endDate = LocalDateTime.of(eDate.plusDays(1), LocalTime.MIN);
		return dbHandler.fetchSittings(startDate, endDate, user.getId());
	}

	/**
	 * Gets all the Sittings that are in the given Date range and belong to the
	 * members of this group from the database.
	 * 
	 * @param sDate the first date at which Sittings are considered
	 * @param eDate the last date at which Sittings are considered
	 * @param group the UserGroup from which the Sittings are taken from
	 * @return all the Sittings belonging to the group members in the given Date
	 *         range.
	 */
	public Set<Sitting> getGroupSittings(LocalDate sDate, LocalDate eDate, UserGroup group) {
		if (group == null) {
			return null;
		}
		Set<Sitting> sittings = new HashSet<>();
		LocalDateTime startDate = LocalDateTime.of(sDate, LocalTime.MIN);
		LocalDateTime endDate = LocalDateTime.of(eDate.plusDays(1), LocalTime.MIN);
		for (int userId : group.getUserIds()) {
			sittings.addAll(dbHandler.fetchSittings(startDate, endDate, userId));
		}
		return sittings;
	}

	/**
	 * Gets all the current user's restrictions.
	 * 
	 * @return all the current user's restrictions, or null if the current user is
	 *         not logged in
	 */
	public List<Restriction> getRestrictions() {
		if (user == null) {
			return null;
		}
		return dbHandler.fetchRestrictions(user.getId());
	}

	/**
	 * Creates and saves Restriction(s) based on restrictionDaySettings. Each
	 * restriction has the same given progName.
	 * 
	 * @param restrictionDaySettings a HashMap that contains weekdays as keys and
	 *                               hours-minutes as values in an Integer array.
	 * @param progName               the program name that the Restriction(s) guard.
	 * @returns false if the current user is not logged in or if the progName is
	 *          null, true otherwise.
	 */
	public boolean saveRestrictions(HashMap<String, Integer[]> restrictionDaySettings, String progName) {
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

	/**
	 * Sends the updated Restriction to the database.
	 * 
	 * @param restriction the Restriction that is updated
	 * @return false if the given Restriction is null or if the current user is not
	 *         logged in or if the Restriction was not updated to the database, true
	 *         otherwise
	 */
	public boolean updateRestriction(Restriction restriction) {
		if (user == null || restriction == null) {
			return false;
		}
		return dbHandler.updateRestriction(restriction);
	}

	/**
	 * Removes the given Restriction from the database.
	 * 
	 * @param restriction the Restriction that is removed
	 * @return false if the given Restriction is null or if the current user is not
	 *         logged in or if the Restriction was not removed from the database,
	 *         true otherwise
	 */
	public boolean removeRestriction(Restriction restriction) {
		if (user == null || restriction == null) {
			return false;
		}
		return dbHandler.deleteRestriction(restriction);
	}

	/**
	 * Gets a List of all the UserGroups the current user owns.
	 * 
	 * @return all the UserGroups the current user owns, null if the current user is
	 *         not logged in.
	 */
	public List<UserGroup> getUserGroups() {
		if (user == null) {
			return null;
		}
		return dbHandler.fetchUserGroups(user.getId());
	}

	/**
	 * Adds the user with the given Id to the group. Before adding, checks that the
	 * Id String is a Integer. Addition fails if the Id is the same as the current
	 * user's Id or if the userGroup already contains a user with the same Id or if
	 * no user with the given Id can be found in the database.
	 * 
	 * @param group     The UserGroup that user is added to
	 * @param userIdStr The added user's Id as a String
	 * @return true if the user was added successfully, false if Id is invalid.
	 */
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

	/**
	 * Removes the user with the given Id from the group. Before removing, checks
	 * that the Id String is a Integer. Removal fails if the Id is the same as the
	 * current user's Id.
	 * 
	 * @param group     The UserGroup that user is removed from
	 * @param userIdStr The removed user's Id as a String
	 * @return true if the user was removed successfully, false if Id is invalid.
	 */
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

	/**
	 * Saves the given UserGroup to the database. Before saving checks that the
	 * group is not empty and has a name. Doesn't save the group if the current user
	 * already owns a group with the same name.
	 * 
	 * @param userGroup The UserGroup that will be saved to the database.
	 * @return false if the given UserGroup was invalid or if the save was
	 *         unsuccessful. True otherwise.
	 */
	public boolean saveUserGroup(UserGroup userGroup) {
		String groupName = userGroup.getGroupName();
		if (userGroup == null || userGroup.getUserIds().size() == 0 || groupName == null || groupName.equals("")) {
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

	/**
	 * Removes the given UserGroup from the database
	 * 
	 * @param userGroup The UserGroup that will be deleted from the database.
	 * @return false if the given UserGroup was null or if the removal was
	 *         unsuccessful. True otherwise.
	 */
	public boolean removeUserGroup(UserGroup userGroup) {
		if (userGroup == null) {
			return false;
		}
		return dbHandler.deleteGroup(userGroup);
	}

	/**
	 * Stops the recorder and logs the user out
	 */
	public void handleLogout() {
		stopRecording();
		user = null;
	}

	/**
	 * Logs the user out and then closes the application window.
	 */
	public void handleExit() {
		handleLogout();
		Stage stage = (Stage) view.getScene().getWindow();
		stage.close();
	}

	/**
	 * Getter
	 * 
	 * @return the current user's username, or null if no user is logged in.
	 */
	public String getUsername() {
		if (user == null) {
			return null;
		}
		return user.getName();
	}

	/**
	 * Getter
	 * 
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
	 * @param name   String that works as a key in the screen-mapping.
	 * @param screen The Pane to be stored.
	 */
	public void addScreen(String name, Pane screen) {
		screens.put(name, screen);
	}

	/**
	 * Activates a screen with the given name
	 * 
	 * @param name String key of the screen-mapping
	 */
	public void activateScreen(String name) {
		view.setRoot(screens.get(name));
	}
}
