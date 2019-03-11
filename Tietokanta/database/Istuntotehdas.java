package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Istuntotehdas creates one Sessionfactory object and returns it.
 * @author JP
 * @version 1.0
 * @since 08/03/2019
 */

public class Istuntotehdas {
	private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();
	private static final SessionFactory ISTUNTOTEHDAS = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

	private Istuntotehdas() {
	}

	/**
	 * This method returns the SessionFactory object.
	 * @return ISTUNTOTEHDAS
	 */
	
	public static SessionFactory annaIstuntotehdas() {
		return ISTUNTOTEHDAS;
	}
}
