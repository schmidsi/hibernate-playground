package ch.fhnw.dbc.project4_db4o;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Hostname {
	private int id;
	
	private String name;
	
	private Website website;
	private Hostname redirectTo;

	private List<Hostname> redirecting = new ArrayList<Hostname>();
	
	public Hostname() {}
	
	public Hostname(String _name) {
		this.name = _name;
	}
	
	public Hostname(String _name, Hostname _redirectTo) {
		this(_name);
		this.redirectTo = _redirectTo;
		this.redirectTo.addRedirect(this);
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Hostname) {
			Hostname comp = (Hostname) obj;
			
			return comp.getHostname().equals(this.name); 
		} else {
			return false;
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void addRedirect(Hostname hostname) {
		this.redirecting.add(hostname);
	}
	
	public void setRedirectTo(Hostname hostname) {
		this.redirectTo = hostname;
	}
	
	public Hostname getRedirect() {
		return redirectTo;
	}
	
	public void setHostname(String name) {
		this.name = name;
	}
	
	public String getHostname() {
		return this.name;
	}

	public Website getWebsite() {
		return website;
	}

	public void setWebsite(Website website) {
		this.website = website;
	}
	
}
