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
public class UC2_WebsiteLookup {
	static ObjectContainer db;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		db = Db4oEmbedded.openFile("db.db4o");
	}
	
	@Test
	public void test01_createClasses() {
		User user = new User("test@example.com", "password");
		Domain domain = new Domain("example.com", user);
		Website website = new Website("Super dbC Website!", domain, user);
		
		db.store(domain);
	}
	
	@Test
	public void test02_lookupHostnameByExample() {
		ObjectSet<Hostname> hostnames = db.queryByExample(new Hostname("www.example.com"));
		
		assertTrue(hostnames.get(0).getHostname().equals("www.example.com"));
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
