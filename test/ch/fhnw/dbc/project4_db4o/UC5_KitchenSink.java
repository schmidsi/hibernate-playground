package ch.fhnw.dbc.project4_db4o;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UC5_KitchenSink {
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
	public void test01_Create2User() {
		OAuth oauth = new OAuth(OAuthProvider.FACEBOOK, "12356", "ASDFB"); 
		User user1 = new User(oauth);
		User user2 = new User("test@example.com", "password");
		
		assertTrue(user1.getId() == 0);
		assertTrue(user2.getId() == 0);
		assertTrue(oauth.getId() == 0);
		
		session.beginTransaction();
		session.save(oauth);
		session.save(user1);
		session.save(user2);
		session.getTransaction().commit();
		
		assertFalse(user1.getId() == 0);
		assertFalse(user2.getId() == 0);
		assertFalse(oauth.getId() == 0);
	}
	
	@Test
	public void test02_CreateWebsite() {
		OAuth oauth = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.FACEBOOK, "12356"))).uniqueResult();
		
		Domain domain = new Domain("example.com", oauth.getUser());
		Website website = new Website("Kitchen Sink", domain, oauth.getUser());
		
		assertTrue(domain.getId() == 0);
		assertTrue(website.getId() == 0);
		
		session.beginTransaction();
		session.save(domain);
		session.save(website);
		session.getTransaction().commit();
		
		assertFalse(domain.getId() == 0);
		assertFalse(website.getId() == 0);
	}
	
	@Test
	public void test03_AuthorizeEditor() {
		Website website = (Website) session.createCriteria(Website.class)
				.add(Restrictions.eq("title", "Kitchen Sink")).uniqueResult();
		User user = (User) session.createCriteria(User.class).add(
				Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		
		website.addAccess(new Access(website, user, Role.READWRITE));
		
		session.beginTransaction();
		session.saveOrUpdate(website);
		session.getTransaction().commit();
		
		assertTrue(website.hasAccess(user, Role.READWRITE));
		assertNotNull(user.getAccess().get(0));
	}
	
	@Test
	public void test04_LookupByUser() {
		User user = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		
		assertNotNull(user);
		assertNotNull(user.getAccess().get(0));
		assertEquals(user.getAccess().get(0).getTarget().getTitle(), "Kitchen Sink");
		assertEquals(user.getAccess().get(0).getTarget().getHostname().getHostname(), "www.example.com");
	}
	
	public void test05_cleanup() {
		Website website = (Website) session.createCriteria(Website.class)
			.add(Restrictions.eq("title", "Kitchen Sink")).uniqueResult();
		
		session.beginTransaction();
		session.delete(website);
		session.getTransaction().commit();
		
		User userDeleted = (User) session.createCriteria(User.class).add(
			Example.create(new User("test@example.com", "")).excludeProperty("password")).uniqueResult();
		OAuth oauthDeleted = (OAuth) session.createCriteria(OAuth.class).add(
			Example.create(new OAuth(OAuthProvider.FACEBOOK, "12356"))).uniqueResult();
		Hostname hostnameDeleted = (Hostname) session.createCriteria(Hostname.class).add(
			Example.create(new Hostname("example.com")).excludeProperty("created")).uniqueResult();
		
		assertNull(userDeleted);
		assertNull(oauthDeleted);
		assertNull(hostnameDeleted);
	}

}
