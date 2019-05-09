package database;

import java.util.List;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * RestrictionAccessObject contains the methods for reading, writing and
 * deleting restrictions for specific programs.
 * 
 * @author miikk
 * @since 05/05/2019
 */

public class RestrictionAccessObject implements RestrictionDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	/**
	 * This method creates a new restriction.
	 * 
	 * @param restriction This is the new restriction that will be created.
	 */
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

	/**
	 * This method updates a pre-existing restriction.
	 * 
	 * @param restriction This is the specified restriction that will be updated.
	 */
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

	/**
	 * This method deletes a pre-existing restriction.
	 * 
	 * @param restriction This is the specified restriction that will be deleted.
	 */
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

	/**
	 * This method returns a list of all restrictions a specific user has.
	 * 
	 * @param userId The id of the user from which the restrictions are read.
	 * @return resultList Returns a list containing all the matching restrictions.
	 */
	@Override
	public List<Restriction> readRestrictions(int userId) {
		Session istunto = istuntotehdas.openSession();
		String query = "from Restriction where userId =:userId";
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Restriction> resultList = (List<Restriction>) istunto.createQuery(query).setParameter("userId", userId)
					.getResultList();
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

	/**
	 * This method returns a list of all restrictions a specific user has on a
	 * specified day of the week.
	 * 
	 * @param weekday The day of the week on which the restriction are on.
	 * @param userId The id of the user from which the restrictions are read.
	 * @return resultList Returns a list containing all the matching restrictions.
	 */
	@Override
	public List<Restriction> readRestrictions(String weekday, int userId) {
		Session istunto = istuntotehdas.openSession();
		String query = "from Restriction where userId =:userId and weekday =:weekday";
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<Restriction> resultList = (List<Restriction>) istunto.createQuery(query).setParameter("userId", userId)
					.setParameter("weekday", weekday).getResultList();
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

	/**
	 * This method returns a list of all restrictions a specific user has on a
	 * specified day of the week, for a specific single program.
	 * 
	 * @param progName The name of the program which is restricted.
	 * @param weekday The day of the week on which the restriction are on.
	 * @param userId The id of the user from which the restrictions are read.
	 * @return resultList Returns a list containing all the matching restrictions.
	 */
	@Override
	public Restriction readRestriction(String progName, String weekday, int userId) {
		Session istunto = istuntotehdas.openSession();
		String query = "from Restriction where prog_name =:progName and weekday =:weekday and userId =:userId";
		try {
			transaktio = istunto.beginTransaction();
			Restriction result = (Restriction) istunto.createQuery(query).setParameter("progName", progName)
					.setParameter("weekday", weekday).setParameter("userId", userId).getSingleResult();
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
