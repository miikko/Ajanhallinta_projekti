package database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * SittingAccessObject contains the methods for reading and writing
 * information into sittings.
 * 
 * @author Arttuhal & miikk
 * @since 13/03/2019
 */

class SittingAccessObject implements SittingDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	/**
	 * This method creates a new sitting.
	 * 
	 * @param sitting This is the new sitting that will be created.
	 */
	@Override
	public boolean createSitting(Sitting sitting) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.save(sitting);
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
	 * This method updates the objects of a pre-existing sitting.
	 * 
	 * @param sitting This is the specified sitting that will be updated.
	 */
	@Override
	public boolean updateSitting(Sitting sitting) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(sitting);
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
	 * This method returns all the sittings within a specific time frame from a specified user.
	 * 
	 * @param start_date This sets the start of the wanted time frame.
	 * @param end_date This sets the end of the wanted time frame.
	 * @param userId UserId is used to specify the user from which the sittings are read.
	 * @return resultSet This is a set containing all the sittings that match the search criteria.
	 */
	@Override
	public Set<Sitting> readSittings(Date start_date, Date end_date, int userId) {
		Session istunto = istuntotehdas.openSession();
		Set<Sitting> resultSet = new HashSet<Sitting>();
		List<Sitting> sittingList;		
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")

			List<Sitting> resultsList = istunto.createQuery("from Sitting where userId =:userId")
					.setParameter("userId", userId).getResultList();
			transaktio.commit();
			sittingList = new ArrayList<Sitting>(resultsList);

		} catch (NoResultException nre) {
			return null;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		for (Sitting sitting : sittingList) {
			try {
				Date sittingStartDate = formatter.parse(sitting.getStart_date());
				Date sittingEndDate = formatter.parse(sitting.getEnd_date());
				if(sittingStartDate.after(start_date) && sittingEndDate.before(end_date)) {
					resultSet.add(sitting);
				}
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		return resultSet;

	}

	// WindowTime methods

	/**
	 * This method creates a new WindowTime object.
	 * 
	 * @param wt This is the new WindowTime that will be created.
	 */
	@Override
	public boolean createWindowTime(WindowTime wt) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.save(wt);
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

	/**
	 * This method updates a pre-existing WindowTime object.
	 * 
	 * @param wt This is the WindowTime object that will be updated.
	 */
	@Override
	public boolean updateWindowTime(WindowTime wt) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.update(wt);
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
