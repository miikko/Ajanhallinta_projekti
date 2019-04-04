package database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class WindowTimeAccessObject {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	public boolean createWindowTime(WindowTime windowTime) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.save(windowTime);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
			System.out.println("WT created");
		}
	}

	public boolean updateWindowTime(WindowTime windowTime) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(windowTime);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
			System.out.println("WT updated");
		}
	}
}
