package models;

public class UserAuth {

	public UserAuth() {
		
	}
	
	/**
	 * Checks the database whether the username and password combination exists<br>
	 * and authorises the login if it does
	 * 
	 * @param username
	 * @param pw
	 * @return the information about the username and password combinations existence
	 */
	public boolean login(String username, String pw) {
		//TODO: Check if username and password exist in database
		
		return false;
	}
	
	/**
	 * Goes through the database to see whether the chosen username already exists
	 * 
	 * @param username
	 * @param pw
	 * @return the information about the username's existence
	 */
	public boolean register(String username, String pw) {
		//TODO: Check if username exists
		
		return false;
	}
}
