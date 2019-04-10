package models;

import database.Kayttaja;
import database.KayttajaAccessObject;

/**
 * Class contains methods for authenticating users and creating
 * Kayttaja-objects.
 * 
 * @author miikk & JP
 * @since 24.3.2019
 */
public class UserAuth {

	public UserAuth() {

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
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		Kayttaja kayttaja = kayttajaDAO.userExists(user_name);
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
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		if (kayttajaDAO.userExists(user_name) == null) {
			Kayttaja newUser = new Kayttaja(user_name, pw);
			kayttajaDAO.createKayttaja(newUser);
			return newUser;
		}
		return null;
	}
	
	public Kayttaja changeUserUsername(int id, String username, String password) {
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		if(kayttajaDAO.userExists(username) != null) {
			return null;
		}else {
			Kayttaja kayt = new Kayttaja(id, username, password);
			kayttajaDAO.updateKayttaja(kayt);
			return kayt;
		}
	}
	
	public Kayttaja changeUserPassword(int id, String username, String pass1, String pass2) {
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		if(!pass1.equals(pass2) || pass1 == "") {
			return null;
		}else {
			Kayttaja kayt = new Kayttaja(id, username, pass1);
			kayttajaDAO.updateKayttaja(kayt);
			return kayt;
		}
	}
}
