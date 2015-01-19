package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name="AppUser")
public class User {
	@Id
	@GeneratedValue
	private int id;
	
	private String password;
	private String email;
	private Date emailConfirmed;
	
	@OneToMany(targetEntity=Access.class, mappedBy="actor",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<Access> access = new ArrayList<Access>();
	
	@OneToMany(targetEntity=OAuth.class, mappedBy="user",
			cascade=CascadeType.ALL, fetch=FetchType.LAZY )
	private List<OAuth> oauths = new ArrayList<OAuth>();
	
	public User(){};
	
	public User(String _email, String _password) {
		this.email = _email;
		this.password = _password;
	}
	
	public User(OAuth _oauth) {
		this.addOAuth(_oauth);
	}
	
	@Override
	public String toString() {
		return "User [email=" + email + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public void setPassword(String _password) {
		this.password = _password;
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
	
	public List<Access> getAccess() {
		ArrayList<Access> al = new ArrayList<Access>();
		
		for (Access a: this.access) {
			al.add(a);
		}
		
		return (List<Access>) al;
	}
	
	public void addOAuth(OAuth _oauth) {
		this.oauths.add(_oauth);
		_oauth.setUser(this);
	}
	
	public void removeOAuth(OAuth _oauth) {
		this.oauths.remove(_oauth);
	}
	
}
