package database;

/**
 * This class handles the link between the GUI, the database and the service
 * 
 * @author Arttuhal
 * @since 13/03/2019
 */
public class DatabaseHandler {

	private KayttajaAccessObject userObject = new KayttajaAccessObject();
	
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
