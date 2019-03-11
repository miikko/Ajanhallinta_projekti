package database;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * KayttajaAccessObject contains methods to store/update/delete/read user objects
 * to and from database.
 * 
 * 
 * @author JP
 * @version 1.0
 * @since 08/03/2019
 */

public class KayttajaAccessObject implements KayttajaDAO_IF{
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;
	
	/**
	 * This method sends user object to the user table.
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
		}catch(Exception e) {
			if(transaktio != null) 
				transaktio.rollback();
			throw e;
			
		}finally {
			istunto.close();
		}
	}

	/**
	 * This method returns one user identified by its id from the user table.
	 * @param id This is the id number of specific user object.
	 * @return kayttaja This is the user which is returned.
	 */
	@Override
	public Kayttaja readKayttaja(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			Kayttaja kayttaja = new Kayttaja();
			istunto.load(kayttaja, id);
			transaktio.commit();
			return kayttaja;
		}catch(Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
		
	}

	/**
	 * This method returns all user object from the user table.
	 * return returnArray This is array of all the user objects.
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
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}

	/**
	 * This method updates specific user object in the user table.
	 * @param kayttaja This is the new data which is being sent to the database.
	 */
	@Override
	public boolean updateKayttaja(Kayttaja kayttaja) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(kayttaja);
			
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}

	/**
	 * This method deletes the user object from the user table.
	 * @param id This is the specific id of an user object to be deleted.
	 */
	@Override
	public boolean deleteKayttaja(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			Kayttaja kayt = readKayttaja(id);
			transaktio = istunto.beginTransaction();
			istunto.delete(kayt);
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}
	
	/**
	 * This method is not yet tested.
	 * It is supposed to sends data updates to user data table.
	 */
	public boolean updateSitting(int id) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(id);
			
			transaktio.commit();
			return true;
		}catch (Exception e) {
			if(transaktio != null)
				transaktio.rollback();
			throw e;
		}finally {
			istunto.close();
		}
	}
}
