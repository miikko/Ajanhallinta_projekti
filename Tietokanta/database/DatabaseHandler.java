package database;

public class DatabaseHandler {

	private KayttajaAccessObject userObject = new KayttajaAccessObject();
	
	// TODO: 
	public boolean sendUpdates(String rData) {
		
		return true;
	}
	
	// TODO: Change login info type to match what Login-class sends
	public boolean login(String username, String password) {
		Kayttaja tempUser = userObject.readKayttaja(username);
		if(tempUser.getPassword() == password) {
			return true;
		}else {
			return false;
		}
	}
}
