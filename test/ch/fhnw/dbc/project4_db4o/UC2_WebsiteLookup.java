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
public class UC2_WebsiteLookup {
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
	public void test01_createClasses() {
		User user = new User("test@example.com", "password");
		Domain domain = new Domain("example.com", user);
		Website website = new Website("Super dbC Website!", domain, user);
		
		assertTrue(user.getId() == 0);
		assertTrue(domain.getId() == 0);
		assertTrue(website.getId() == 0);
		
		session.beginTransaction();
		session.save(user);
		session.save(domain);
		session.save(website);
		session.getTransaction().commit();
		
		assertFalse(user.getId() == 0);
		assertFalse(domain.getId() == 0);
		assertFalse(website.getId() == 0);
	}
	
	@Test
	public void test02_lookupHostnameByExample() {
		Hostname hostname = new Hostname("example.com");
		Example hostnameExample = Example.create(hostname).excludeProperty("created");
		Hostname resultHostname = (Hostname) session.createCriteria(Hostname.class).add(hostnameExample).uniqueResult();
		
		assertFalse(resultHostname.getId() == 0);
		assertTrue(resultHostname.getRedirect().getHostname().equals("www.example.com"));
		assertTrue(resultHostname.getRedirect().getWebsite().getTitle().equals("Super dbC Website!"));
		assertNull(resultHostname.getWebsite());
	}
	
	@Test
	public void test03_deleteWebsiteCascade() {
		Hostname hostname = new Hostname("example.com");
		Example hostnameExample = Example.create(hostname).excludeProperty("created");
		Hostname resultHostname = (Hostname) session.createCriteria(Hostname.class).add(hostnameExample).uniqueResult();
		
		session.beginTransaction();
		session.delete(resultHostname.getRedirect().getWebsite());
		session.delete(resultHostname.getRedirect());
		session.delete(resultHostname);
		session.getTransaction().commit();
		
		Hostname domain = (Hostname) session.createCriteria(Hostname.class).add(
			Example.create(new Hostname("example.com")).excludeProperty("created")).uniqueResult();
		Hostname subdomain = (Hostname) session.createCriteria(Hostname.class).add(
				Example.create(new Hostname("www.example.com")).excludeProperty("created")).uniqueResult();
		
		assertNull(domain);
		assertNull(subdomain);
	}

}
