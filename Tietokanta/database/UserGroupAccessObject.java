package database;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * UserGroupAccessObject contains methods for reading, writing and deleting user groups.
 * 
 * @author Arttuhal & miikk
 * @since 27/04/2019
 */

class UserGroupAccessObject implements UserGroupDAO_IF {
	SessionFactory istuntotehdas = Istuntotehdas.annaIstuntotehdas();
	Transaction transaktio = null;

	/**
	 * This method creates a new user group.
	 * 
	 * @param userGroup This is the new userGroup that will be created.
	 */
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

	/**
	 * This method updates a pre-existing user group.
	 * 
	 * @param userGroup This is the specified user group that will be updated.
	 */
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

	/**
	 * This method deletes a pre-existing user group.
	 * 
	 * @param userGroup This is used to define the user group that will be deleted.
	 */
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

	/**
	 * This method returns all the user groups with a specific admin user.
	 * 
	 * @param adminId Admin id is the user id that filters out only the specific user groups.
	 * @return userGroups This is a list of all the user groups with the given admin user.
	 */
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
	
}
