package database;

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
		return userObject.readKayttaja(username);
	}
	
	public Kayttaja fetchUser(int userId) {
		return userObject.readKayttaja(userId);
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
		return groupObject.createGroup(group);
	}
	
	public List<UserGroup> fetchUserGroups(int adminId) {
		return groupObject.readGroups(adminId);
	}
	
	public boolean updateGroup(UserGroup group) {
		return groupObject.updateGroup(group);
	}
	
	public boolean deleteGroup(UserGroup group) {
		return groupObject.deleteGroup(group);
	}
	
}
