package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;
import java.util.Date;

public class User {
	private String password;
	private String email;
	private Date emailConfirmed;
	
	private ArrayList<Access> access = new ArrayList<Access>();
	private ArrayList<OAuth> oauths = new ArrayList<OAuth>();
	
	public User(){};
	
	public User(String _email, String _password) {
		this.email = _email;
		this.password = _password;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public Boolean checkPassword(String _password) {
		return this.password.equals(_password);
	}
	
	public Date getEmailConfirmed() {
		return this.emailConfirmed;
	}
	
	public void confirmEmail() {
		this.emailConfirmed = new Date();
	}
	
	public void addAccess(Access _access) {
		this.access.add(_access);
	}
	
	public void removeAccess(Access _access) {
		this.access.remove(_access);
	}
	
	public void addOAuth(OAuth _oauth) {
		this.oauths.add(_oauth);
	}
	
	public void removeOAuth(OAuth _oauth) {
		this.oauths.remove(_oauth);
	}
	
}
