package ch.fhnw.dbc.project4_db4o;

import static org.junit.Assert.*;

import java.io.File;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UC1_DomainRegistration {
	private static User user;
	private static Domain domain;
	static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Db4oEmbedded.openFile("db.db4o");
	}
	
	@Test
	public void test01_SubdomainCreation() {
		user = new User("test@example.com", "password");
		domain = new Domain("example.com", user);
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
		db.store(domain);
	}
	
	@Test
	public void test04_Query() {
		ObjectSet<Hostname> domains = db.query(Hostname.class);
		
		// one subdomain and one domain
		assertTrue(domains.size() == 2);
	}
	
	@AfterClass
	public static void clear() {
		db.close();
		
		try {
			File file = new File("db.db4o");
			file.delete();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
}
