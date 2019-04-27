package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * UserGroupAccessObject contains methods for reading and writing user groups.
 * 
 * @author Arttuhal
 * @since 27/04/2019
 */

public class UserGroupAccessObject implements UserGroupDAO_IF{
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;
	
	@Override
	public boolean createGroup(UserGroup userGroup) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.save(userGroup);
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
	
	@Override
	public boolean updateGroup(UserGroup userGroup) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(userGroup);
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
	
	

}
