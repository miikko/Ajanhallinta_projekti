package models;

import database.Kayttaja;
import database.KayttajaAccessObject;

/**
 * Class contains methods for authenticating users and creating Kayttaja-objects.
 * @author miikk
 * @since 24.3.2019
 */
public class UserAuth {

	public UserAuth() {
		
	}
	
	/**
	 * Checks the database for a user with the specified username and password combination.
	 * @param username
	 * @param pw
	 * @return the user with matching credentials or null if no such user is found.
	 */
	public Kayttaja login(String username, String pw) {
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		Kayttaja[] kayttajat = kayttajaDAO.readKayttajat();
		for (Kayttaja kayttaja : kayttajat) {
			if (kayttaja.getName().equals(username) && kayttaja.getPassword().equals(pw)) {
				return kayttaja;
			}
		}
		return null;
	}
	
	/**
	 * Goes through all the users in the database to see whether the chosen username already exists.<br>
	 * If it is not taken, creates a new user and adds it to the database
	 * @param username
	 * @param pw
	 * @return the created Kayttaja-object or null if the username was taken.
	 */
	public Kayttaja register(String username, String pw) {
		KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
		Kayttaja[] kayttajat = kayttajaDAO.readKayttajat();
		Kayttaja newUser = new Kayttaja(username, pw);
		for (Kayttaja kayttaja : kayttajat) {
			if (kayttaja.getName().equals(username)) {
				return null;
			}
		}
		kayttajaDAO.createKayttaja(newUser);
		return newUser;
	}
}
