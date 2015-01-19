package ch.fhnw.dbc.project4_db4o;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UC5_KitchenSink {
	static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Db4oEmbedded.openFile("db.db4o");
	}

	@Test
	public void test01_Create2User() {
		OAuth oauth = new OAuth(OAuthProvider.FACEBOOK, "12356", "ASDFB"); 
		User user1 = new User(oauth);
		User user2 = new User("test@example.com", "password");
	
		db.store(user1);
		db.store(user2);
	}
	
	@Test
	public void test02_CreateWebsite() {
		OAuth oauth = db.query(OAuth.class).get(0);
		
		Domain domain = new Domain("example.com", oauth.getUser());
		Website website = new Website("Kitchen Sink", domain, oauth.getUser());
		
		db.store(website);
	}
	
	@Test
	public void test03_AuthorizeEditor() {
		Website website = db.query(Website.class).get(0);
		User user = db.query(User.class).get(0);
		
		website.addAccess(new Access(website, user, Role.READWRITE));
		
		db.store(website);
		
		assertTrue(website.hasAccess(user, Role.READWRITE));
		assertNotNull(user.getAccess().get(0));
	}
	
	@Test
	public void test04_LookupByUser() {
		User user = db.query(User.class).get(0);
		
		assertNotNull(user);
		assertNotNull(user.getAccess().get(0));
		assertEquals(user.getAccess().get(0).getTarget().getTitle(), "Kitchen Sink");
		assertEquals(user.getAccess().get(0).getTarget().getHostname().getHostname(), "www.example.com");
	}
	
	@Test
	public void test05_cleanup() {
		Website website = db.query(Website.class).get(0);
		OAuth oauth = db.query(OAuth.class).get(0);
		ObjectSet<User> users = db.query(User.class);
		ObjectSet<Hostname> hostnames = db.query(Hostname.class);
		
		
		db.delete(website);
		db.delete(oauth);
		for (User u: users) db.delete(u);
		for (Hostname h: hostnames) db.delete(h);
		
		ObjectSet<User> userDeleted = db.query(User.class);
		ObjectSet<OAuth> oauthDeleted = db.query(OAuth.class);
		ObjectSet<Hostname> hostnameDeleted = db.query(Hostname.class);
		
		assertTrue(userDeleted.size() == 0);
		assertTrue(oauthDeleted.size() == 0);
		assertTrue(hostnameDeleted.size() == 0);
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
