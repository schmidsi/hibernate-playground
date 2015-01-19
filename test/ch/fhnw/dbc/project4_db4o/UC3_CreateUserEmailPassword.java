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
public class UC3_CreateUserEmailPassword {
	static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Db4oEmbedded.openFile("db.db4o");
	}

	@Test
	public void test01_CreateUser() {
		User user = new User("test@example.com", "password");
		
		db.store(user);
	}
	
	@Test
	public void test02_LookupUserTestPW() {
		ObjectSet<User> users = db.query(User.class);
		User user = users.get(0);
		
		assertNotNull(user);
		assertFalse(user.checkPassword("wrongPassword"));
		assertTrue(user.checkPassword("password"));
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
