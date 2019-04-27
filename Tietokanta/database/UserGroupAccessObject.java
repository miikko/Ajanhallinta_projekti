package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * UserGroupAccessObject contains methods for reading and writing user groups.
 * 
 * @author Arttuhal
 * @since 27/04/2019
 */

class UserGroupAccessObject implements UserGroupDAO_IF {
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

	@Override
	public boolean deleteGroup(UserGroup userGroup) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			istunto.delete(userGroup);
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
	public List<UserGroup> readGroups(int adminId) {
		Session istunto = istuntotehdas.openSession();
		try {
			transaktio = istunto.beginTransaction();
			@SuppressWarnings("unchecked")
			List<UserGroup> userGroups = (List<UserGroup>) istunto.createQuery("from UserGroup where adminId =:adminId").setParameter("adminId", adminId).getResultList();
			transaktio.commit();
			return userGroups;
		} catch (Exception e) {
			if (transaktio != null)
				transaktio.rollback();
			throw e;
		} finally {
			istunto.close();
		}
	}

//	@Override
//	public boolean addUser(int userId) {
//		Session istunto = istuntotehdas.openSession();
//		try {
//			transaktio = istunto.beginTransaction();
//			istunto.save(userId);
//			transaktio.commit();
//			return true;
//
//		} catch (NoResultException nre) {
//			return false;
//		} catch (Exception e) {
//			if (transaktio != null)
//				transaktio.rollback();
//			throw e;
//		} finally {
//			istunto.close();
//		}
//	}
//	
//	@Override
//	public boolean addAdmin(int adminId) {
//		Session istunto = istuntotehdas.openSession();
//		try {
//			transaktio = istunto.beginTransaction();
//			istunto.save(adminId);
//			transaktio.commit();
//			return true;
//
//		} catch (NoResultException nre) {
//			return false;
//		} catch (Exception e) {
//			if (transaktio != null)
//				transaktio.rollback();
//			throw e;
//		} finally {
//			istunto.close();
//		}
//	}

}
