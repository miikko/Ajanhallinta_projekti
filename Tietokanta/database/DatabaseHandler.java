package database;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * This class handles the link between the GUI, the database and the service
 * 
 * @author miikk
 * @since 13/03/2019
 */
public class DatabaseHandler {

	private KayttajaAccessObject userObject;
	private SittingAccessObject sittingObject;
	private UserGroupAccessObject groupObject;
	private RestrictionAccessObject restrictObject;
	private ConnectionHandler connHandler;
	
	private DatabaseHandler() {
		connHandler = ConnectionHandler.getInstance();
		connHandler.openTunnel();
		userObject = new KayttajaAccessObject();
		sittingObject = new SittingAccessObject();
		groupObject = new UserGroupAccessObject();
		restrictObject = new RestrictionAccessObject();
	}
	
	private static class SingletonHolder {
		private static final DatabaseHandler INSTANCE = new DatabaseHandler();
	}
	
	public static DatabaseHandler getInstance() {
		return SingletonHolder.INSTANCE;
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
	
	public void deleteUser(String username) {
		userObject.deleteKayttaja(username);
	}
	
	//Sitting methods
	
	public boolean sendSitting(Sitting sitting) {
		return sittingObject.createSitting(sitting);
	}
	
	public Set<Sitting> fetchSittings(LocalDateTime startDate, LocalDateTime endDate, int userId) {
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
	
	//Restriction methods
	
	public boolean sendRestriction(Restriction restriction) {
		return restrictObject.createRestriction(restriction);
	}
	
	public List<Restriction> fetchRestrictions(int userId) {
		return restrictObject.readRestrictions(userId);
	}
	
	public List<Restriction> fetchRestrictions(String weekday, int userId) {
		return restrictObject.readRestrictions(weekday, userId);
	}
	
	public Restriction fetchRestriction(String progName, String weekday, int userId) {
		return restrictObject.readRestriction(progName, weekday, userId);
	}
	
	public boolean updateRestriction(Restriction restriction) {
		return restrictObject.updateRestriction(restriction);
	}
	
	public boolean deleteRestriction(Restriction restriction) {
		return restrictObject.deleteRestriction(restriction);
	}
	
}
