package models;

import database.Kayttaja;
import database.KayttajaAccessObject;
import database.Sitting;
import database.SittingAccessObject;

/**
 * Class contains methods for authenticating users and creating
 * Kayttaja-objects.
 * 
 * @author miikk
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
		} else {
			SittingAccessObject sittingDAO = new SittingAccessObject();
			Sitting sitting = new Sitting(kayttaja);
			sittingDAO.createSitting(sitting);
			System.out.println(kayttaja.getSittings().size());
			return kayttaja;
		}
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
		} else {
			return null;
		}
	}
}
