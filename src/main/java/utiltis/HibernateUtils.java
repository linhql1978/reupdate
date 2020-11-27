package utiltis;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import qualifier.HibernateSession;

public class HibernateUtils {

	private static SessionFactory sessionFactory;

	private HibernateUtils() {
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml")
					.build();

			Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();

			sessionFactory = metadata.getSessionFactoryBuilder().build();
		}

		return sessionFactory;
	}

	@Produces
	@RequestScoped
	@HibernateSession
	public static Session getSession() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		session.beginTransaction();

		return session;
	}

	public static void close(@Disposes @HibernateSession Session session) {
		session.getTransaction().commit();
		session.close();
	}
}
