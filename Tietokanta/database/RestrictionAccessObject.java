package database;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class RestrictionAccessObject implements RestrictionDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	@Override
	public boolean createRestriction(Restriction restriction) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.save(restriction);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		} finally {
			istunto.close();
		}
	}

	@Override
	public boolean updateRestriction(Restriction restriction) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(restriction);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		} finally {
			istunto.close();
		}
	}
	
	@Override
	public boolean deleteRestriction(Restriction restriction) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.delete(restriction);
			transaktio.commit();
			return true;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		} finally {
			istunto.close();
		}
	}

	@Override
	public List<Restriction> readRestrictions(String weekday, int userId) {
		Session istunto = istuntotehdas.openSession();
		String query = "from Restriction where userId =:userId and weekday =:weekday";
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Restriction> resultList = (List<Restriction>) istunto.createQuery(query).setParameter("userId", userId).setParameter("weekday", weekday).getResultList();
			transaktio.commit();
			return resultList;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		} finally {
			istunto.close();
		}
	}

	@Override
	public Restriction readRestriction(String progName, String weekday, int userId) {
		Session istunto = istuntotehdas.openSession();
		String query = "from Restriction where prog_name =:progName and weekday =:weekday and userId =:userId";
		try {
			transaktio = istunto.beginTransaction();
			Restriction result = (Restriction) istunto.createQuery(query).setParameter("progName", progName).setParameter("weekday", weekday).setParameter("userId", userId).getSingleResult();
			transaktio.commit();
			return result;
		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			if (transaktio != null) {
				transaktio.rollback();
			}
			throw e;
		} finally {
			istunto.close();
		}
	}

}
