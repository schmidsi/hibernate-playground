package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;
import java.util.Date;

public class Hostname {
	private String name;
	private Date created;
	
	private Hostname redirectTo;
	
	private ArrayList<Hostname> redirecting = new ArrayList<Hostname>();
	
	public Hostname() {}
	
	public Hostname(String _name) {
		this.name = _name;
		this.created = new Date();
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
	
	public Date getCreated() {
		return this.created;
	}
	
}
