package database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Istuntotehdas {
	private static final StandardServiceRegistry REGISTRY = new StandardServiceRegistryBuilder().configure().build();
	private static final SessionFactory ISTUNTOTEHDAS = new MetadataSources(REGISTRY).buildMetadata().buildSessionFactory();

	private Istuntotehdas() {
	}

	public static SessionFactory annaIstuntotehdas() {
		return ISTUNTOTEHDAS;
	}
}
