package ch.fhnw.dbc.project4_db4o;

import static org.junit.Assert.*;

import org.hibernate.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UC1_DomainRegistration {
	private static User user;
	private static Domain domain;
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
	public void test01_SubdomainCreation() {
		user = new User("test@example.com", "password");
		domain = new Domain("example.com", user);
		
		assertTrue(user.getId() == 0);
		assertTrue(domain.getId() == 0);
	}

	@Test
	public void test02_SubdomainCreationVerify() {		
		// domains have automatically a www. subdomain. the main domain redirects to this 
		// subdomain. i.e. example.com redirects to www.example.com
		Subdomain wwwSubdomain = domain.getSubdomain(0);
		
		assertTrue(wwwSubdomain.getHostname().equals("www.example.com"));
		assertTrue(domain.getRedirect().equals(wwwSubdomain));
		assertTrue(domain.hasAccess(user, Role.OWN));
	}
	
	@Test
	public void test03_HibernateSave() {
		session.beginTransaction();
		
		session.save(user);
		session.save(domain);
		
		assertFalse(user.getId() == 0);
		assertFalse(domain.getId() == 0);
		
		session.getTransaction().commit();
	}
	
	@Test
	public void test04_HibernateQuery() {
		Domain domainFromHibernate = (Domain) session.createCriteria(Domain.class)
				.add(Restrictions.eq("name", "example.com")).uniqueResult();
		
		assertNotNull(domainFromHibernate);
		assertTrue(domainFromHibernate.equals(domain));
		
		Subdomain subdomainFromHibernate = (Subdomain) session.createCriteria(Subdomain.class)
				.add(Restrictions.eq("name", "www.example.com")).uniqueResult();
		
		assertNotNull(subdomainFromHibernate);
		assertTrue(domainFromHibernate.getRedirect().equals(subdomainFromHibernate));
	}
	
	@Test
	public void test05_Cleanup() {			
		session.beginTransaction();
		session.delete(domain);
		session.delete(user);
		session.getTransaction().commit();
		
		Domain domainFromHibernate = (Domain) session.createCriteria(Domain.class)
				.add(Restrictions.eq("name", "example.com")).uniqueResult();
		
		User userFromHibernate = (User) session.createCriteria(User.class)
				.add(Restrictions.eq("email", "test@example.com")).uniqueResult();
		
		assertNull(domainFromHibernate);
		assertNull(userFromHibernate);
	}
}
