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
public class UC4_UserOAuth {
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
	public void test01_CreateUserByOAuth() {
		OAuth oauth = new OAuth(OAuthProvider.FACEBOOK, "12356", "ASDFB"); 
		User user = new User(oauth);
		
		assertTrue(user.getId() == 0);
		assertTrue(oauth.getId() == 0);
		
		session.beginTransaction();
		session.save(oauth);
		session.save(user);
		session.getTransaction().commit();
		
		assertFalse(user.getId() == 0);
		assertFalse(oauth.getId() == 0);
	}
	
	@Test
	public void test02_LookupUserByOAuth() {
		OAuth oauth = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.FACEBOOK, "12356"))).uniqueResult();
		
		assertNotNull(oauth);
		assertNotNull(oauth.getUser());
	}
	
	@Test
	public void test03_SetLateEmailPassword() {
		OAuth oauth = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.FACEBOOK, "12356"))).uniqueResult();
		User user = oauth.getUser();
		
		user.setEmail("test@example.com");
		user.setPassword("password");
		
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		assertTrue(user.checkPassword("password"));
	}
	
	@Test
	public void test04_SecondOAuth() {
		User user = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		OAuth oauth = new OAuth(OAuthProvider.TWITTER, "76532", "QWRE");
		
		user.addOAuth(oauth);
		
		assertTrue(oauth.getId() == 0);
		
		session.beginTransaction();
		session.save(user);
		session.getTransaction().commit();
		
		assertFalse(oauth.getId() == 0);
	}
	
	@Test
	public void test05_LookupSecondOAuth() {
		OAuth oauth = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.TWITTER, "76532"))).uniqueResult();
		User user = oauth.getUser();
		
		assertNotNull(user);
		assertTrue(user.checkPassword("password"));
	}
	
	@Test
	public void test06_DeleteAll() {
		OAuth oauth = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.TWITTER, "76532"))).uniqueResult();
		User user = oauth.getUser();
		
		session.beginTransaction();
		session.delete(user);
		session.getTransaction().commit();
		
		User userDeleted = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		OAuth oauth1 = (OAuth) session.createCriteria(OAuth.class).add(
				Example.create(new OAuth(OAuthProvider.TWITTER, "76532"))).uniqueResult();
		OAuth oauth2 = (OAuth) session.createCriteria(OAuth.class).add(
				Example.create(new OAuth(OAuthProvider.FACEBOOK, "12356"))).uniqueResult();
	
		assertNull(userDeleted);
		assertNull(oauth1);
		assertNull(oauth2);
	}

}
