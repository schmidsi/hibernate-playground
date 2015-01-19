package ch.fhnw.dbc.project3_hibernate;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class UC1_DomainRegistration {
	private static User user;
	private static Domain domain;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		user = new User("test@example.com", "password");
		domain = new Domain("example.com", user);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testSubdomainCreation() {		
		// domains have automatically a www. subdomain. the main domain redirects to this 
		// subdomain. i.e. example.com redirects to www.example.com
		Subdomain wwwSubdomain = domain.getSubdomain(0);
		
		assertTrue(wwwSubdomain.getHostname().equals("www.example.com"));
		assertTrue(domain.getRedirect().equals(wwwSubdomain));
		assertTrue(domain.hasAccess(user, Role.OWN));
	}

}
