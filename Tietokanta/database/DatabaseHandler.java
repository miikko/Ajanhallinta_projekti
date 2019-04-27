package database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * This class handles the link between the GUI, the database and the service
 * 
 * @author Arttuhal & miikk
 * @since 13/03/2019
 */
public class DatabaseHandler {

	private KayttajaAccessObject userObject;
	private SittingAccessObject sittingObject;
	private UserGroupAccessObject groupObject;
	private ConnectionHandler connHandler;
	
	public DatabaseHandler() {
		connHandler = ConnectionHandler.getInstance();
		connHandler.openTunnel();
		userObject = new KayttajaAccessObject();
		sittingObject = new SittingAccessObject();
		groupObject = new UserGroupAccessObject();
	}
	
	//User methods
	
	public boolean sendUser(Kayttaja user) {
		return userObject.createKayttaja(user);
	}
	
	public Kayttaja fetchUser(String username) {
		return userObject.userExists(username);
	}
	
	public boolean updateUser(Kayttaja user) {
		return userObject.updateKayttaja(user);
	}
	
	//Sitting methods
	
	public boolean sendSitting(Sitting sitting) {
		return sittingObject.createSitting(sitting);
	}
	
	public Set<Sitting> fetchSittings(Date startDate, Date endDate, int userId) {
		return sittingObject.readSittings(startDate, endDate, userId);
	}
	
	public boolean updateSitting(Sitting sitting) {
		return sittingObject.updateSitting(sitting);
	}
	
	//WindowTime methods
	
	public boolean sendWindowTime(WindowTime wt) {
		return sittingObject.createWindowTime(wt);
	}
	
	public boolean updateWindowTime(WindowTime wt) {
		return sittingObject.updateWindowTime(wt);
	}
	
	//UserGroup methods
	
	public boolean sendUserGroup(UserGroup group) {
		return false;
	}
	
	public List<UserGroup> fetchUserGroups(int adminId) {
		List<UserGroup> groups = new ArrayList<>();
		return groups;
	}
	
	public boolean addUserToGroup(UserGroup group, int userId) {
		return false;
	}
	
	/**
	 * This method receives recorded data from the service and sends it to the active sitting in the database.
	 * @param rData This is the data that is sent by the Recorder class
	 * @return boolean: true if transaction succeeds, false if it fails
	 */
	
	// TODO: Once Sitting and related classes are finished, connect with them
	public boolean sendUpdates(String rData) {
		
		return true;
	}
	
	/**
	 * This method checks if the entered login information is correct.
	 * @param username The username sent from GUI login
	 * @param password The password sent from GUI login
	 * @return boolean: true if login succeeds, false if it fails
	 */
	// TODO: Change login info type to match what Login-class sends
//	public boolean login(String username, String password) {
//		Kayttaja tempUser = userObject.readKayttaja(username);
//		if (tempUser.getPassword() == password) {
//			return true;
//		} else {
//			return false;
//		}
//	}
}
