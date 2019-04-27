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

public class SittingAccessObject implements SittingDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

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
