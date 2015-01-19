package ch.fhnw.dbc.project4_db4o;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UC3_CreateUserEmailPassword {
	static SessionFactory sessionFactory;
	static Session session;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configuration configuration = new Configuration();
		configuration.configure("/hibernate.cfg.xml");
		sessionFactory = configuration.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build());
		session = sessionFactory.openSession();
	}

	@Test
	public void test01_CreateUser() {
		User user = new User("test@example.com", "password");
		
		assertTrue(user.getId() == 0);
		
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		assertFalse(user.getId() == 0);
	}
	
	@Test
	public void test02_LookupUserTestPW() {
		User user = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		
		assertNotNull(user);
		assertFalse(user.checkPassword("wrongPassword"));
		assertTrue(user.checkPassword("password"));
	}
	
	@Test
	public void test03_deleteUser() {
		User user = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		
		User checkuser = (User) session.createCriteria(User.class).add(
			Example.create(new User("example.com", "")).excludeProperty("password")).uniqueResult();
		
		assertNull(checkuser);
	}

}
