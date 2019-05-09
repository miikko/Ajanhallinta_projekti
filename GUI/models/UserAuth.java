package models;

import database.DatabaseHandler;
import database.Kayttaja;

/**
 * Class contains methods for authenticating users and creating
 * Kayttaja-objects.
 * 
 * @author miikk & JP
 * @since 24.3.2019
 */
public class UserAuth {
	private DatabaseHandler dbHandler;

	public UserAuth(DatabaseHandler dbHandler) {
		this.dbHandler = dbHandler;
	}

	/**
	 * Checks the database for a user with the specified username and password
	 * combination.
	 * 
	 * @param username
	 * @param pw
	 * @return the user with matching credentials or null if no such user is found.
	 */
	public Kayttaja login(String user_name, String pw) {
		Kayttaja kayttaja = dbHandler.fetchUser(user_name);
		if (kayttaja == null || !kayttaja.getPassword().equals(pw)) {
			return null;
		}
		return kayttaja;
	}

	/**
	 * Goes through all the users in the database to see whether the chosen username
	 * already exists.<br>
	 * If it is not taken, creates a new user and adds it to the database
	 * 
	 * @param username
	 * @param pw
	 * @return the created Kayttaja-object or null if the username was taken.
	 */
	public Kayttaja register(String user_name, String pw) {
		if (user_name.equals("") || pw.equals("")) {
			System.out.println("Ei saa tyhjää");
			return null;
		}
		if (dbHandler.fetchUser(user_name) == null) {
			Kayttaja newUser = new Kayttaja(user_name, pw);
			dbHandler.sendUser(newUser);
			return newUser;
		}
		return null;
	}

	
	/**
	 * Checks if the given username is not empty or the username can be found in the database.
	 * @param id
	 * @param username
	 * @param password
	 * @return the kayttaja-object with the new username or null if requirements were not met.
	 */
	public Kayttaja changeUsername(int id, String username, String password) {
		if (username.equals("") || password.equals("") || dbHandler.fetchUser(username) != null) {
			return null;
		} else {
			Kayttaja kayt = new Kayttaja(id, username, password);
			dbHandler.updateUser(kayt);
			return kayt;
		}
	}

	/**
	 * Checks if the given passwords do not match or they are empty.
	 * @param id
	 * @param username
	 * @param newPw
	 * @param newPwRepeated
	 * @return the kayttaja-object with the new password or null if the requirements were not met.
	 */
	public Kayttaja changePassword(int id, String username, String newPw, String newPwRepeated) {
		if (!newPwRepeated.equals(newPw) || newPw.equals("")) {
			return null;
		} else {
			Kayttaja kayt = new Kayttaja(id, username, newPw);
			dbHandler.updateUser(kayt);
			return kayt;
		}
	}
}
