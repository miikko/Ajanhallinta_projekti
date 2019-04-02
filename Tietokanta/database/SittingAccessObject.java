package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * SittingAccessObject will contain the methods for reading and writing
 * information into sittings.
 * 
 * @author Arttuhal & miikk
 * @since 13/03/2019
 */

public class SittingAccessObject implements SittingDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	@Override
	public boolean createSitting(Sitting sitting) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(sitting);
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
	 * This method is not yet tested. It is supposed to sends data updates to user
	 * data table.
	 */
	@Override
	public boolean updateSitting(Sitting sitting) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(sitting);
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
