package database;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * KayttajaAccessObject contains methods to store/update/delete/read user
 * objects to and from database.
 * 
 * 
 * @author JP
 * @version 1.0
 * @since 08/03/2019
 */

class KayttajaAccessObject implements KayttajaDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	/**
	 * This method sends user object to the user table.
	 * 
	 * @param kayttaja This is the user object to be stored.
	 */
	@Override
	public boolean createKayttaja(Kayttaja kayttaja) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(kayttaja);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;

		} finally {
			istunto.close();
		}
	}

	/**
	 * This method returns one user identified by its id from the user table.
	 * 
	 * @param user_name This is the username of specific user object to be read.
	 * @return kayttaja This is the user which is returned.
	 */
	@Override
	public Kayttaja readKayttaja(String user_name) {
		Session istunto = istuntotehdas.openSession();
		try {
			try {
				transaktio = istunto.beginTransaction();
				Kayttaja kayttaja = (Kayttaja) istunto.createQuery("from Kayttaja where user_name =:user_name")
						.setParameter("user_name", user_name).getSingleResult();
				transaktio.commit();
				return kayttaja;
			} catch (NoResultException nre) {
				System.out.println("Ei käyttäjää");
				return null;
			}
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}

	}

	/**
	 * This method returns all user object from the user table. return returnArray
	 * This is array of all the user objects.
	 */
	@Override
	public Kayttaja[] readKayttajat() {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Kayttaja> kayttajat = istunto.createQuery("from Kayttaja").getResultList();
			transaktio.commit();
			Kayttaja[] returnArray = new Kayttaja[kayttajat.size()];
			return (Kayttaja[]) kayttajat.toArray(returnArray);
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
	}

	/**
	 * This method updates specific user object in the user table.
	 * 
	 * @param kayttaja This is the new data which is being sent to the database.
	 */
	@Override
	public boolean updateKayttaja(Kayttaja kayttaja) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(kayttaja);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
	}

	/**
	 * This method deletes the user object from the user table.
	 * 
	 * @param user_nameThis is the specific username of an user object to be
	 *                      deleted.
	 */
	@Override
	public boolean deleteKayttaja(String user_name) {
		Session istunto = istuntotehdas.openSession();
		try {
			try {
				Kayttaja kayt = (Kayttaja) istunto.createQuery("from Kayttaja where user_name =:user_name")
						.setParameter("user_name", user_name).getSingleResult();
				transaktio = istunto.beginTransaction();
				istunto.delete(kayt);
				transaktio.commit();
				return true;
			} catch (NoResultException nre) {
				System.out.println("Ei löytynyt käyttäjää");
				return false;
			}
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
	}

	// Palauttaa käyttäjän usernamella käyttäjä objectin usernamen ja
	// salasanan jos se löytyy, muuten palauttaa nullin.
	public Kayttaja userExists(String user_name) {
		Session istunto = istuntotehdas.openSession();
		try {

			transaktio = istunto.beginTransaction();
			Kayttaja kayttaja = (Kayttaja) istunto.createQuery("from Kayttaja where user_name =:user_name")
					.setParameter("user_name", user_name).getSingleResult();
			return kayttaja;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
	}
}
