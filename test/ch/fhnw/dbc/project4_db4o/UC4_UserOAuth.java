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
public class UC4_UserOAuth {
	static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Db4oEmbedded.openFile("db.db4o");
	}

	@Test
	public void test01_CreateUserByOAuth() {
		OAuth oauth = new OAuth(OAuthProvider.FACEBOOK, "12356", "ASDFB"); 
		User user = new User(oauth);
		
		db.store(user);
	}
	
	@Test
	public void test02_LookupUserByOAuth() {
		ObjectSet<OAuth> q = db.query(OAuth.class);
		OAuth oauth = q.get(0);
		
		assertNotNull(oauth);
		assertNotNull(oauth.getUser());
	}
	
	@Test
	public void test03_SetLateEmailPassword() {
		ObjectSet<OAuth> q = db.query(OAuth.class);
		OAuth oauth = q.get(0);
		User user = oauth.getUser();
		
		user.setEmail("test@example.com");
		user.setPassword("password");
	
		db.store(user);
		
		assertTrue(user.checkPassword("password"));
	}
	
	@Test
	public void test04_SecondOAuth() {
		ObjectSet<User> q = db.query(User.class);
		User user = q.get(0);
		OAuth oauth = new OAuth(OAuthProvider.TWITTER, "76532", "QWRE");
		
		user.addOAuth(oauth);
		
		db.store(user);
		db.store(oauth);
	}
	
	@Test
	public void test05_LookupSecondOAuth() {
		ObjectSet<OAuth> q = db.query(OAuth.class);
		
		OAuth oauth = q.get(1);
		User user = oauth.getUser();
		
		assertNotNull(user);
		assertTrue(user.checkPassword("password"));
	}
	
	@Test
	public void test06_DeleteAll() {
		ObjectSet<OAuth> q = db.query(OAuth.class);
		OAuth oauth = q.get(1);
		User user = oauth.getUser();
		
		db.delete(user);
		
		for (OAuth oa: q) db.delete(oa);
		
		ObjectSet<User> qu = db.query(User.class);
		ObjectSet<OAuth> qo = db.query(OAuth.class);
		
		assertTrue(qu.size() == 0);
		assertTrue(qo.size() == 0);
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
