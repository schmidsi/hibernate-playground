package ch.fhnw.dbc.project3_hibernate;

import java.util.ArrayList;

public class Website implements AccessControllable {
	private Hostname hostname;
	
	private ArrayList<Access> access = new ArrayList<Access>();
	
	public Website() {};
	
	public Website(Hostname _hostname, User _owner) {
		this.setHostname(_hostname);
		this.addAccess(new Access(this, _owner, Role.OWN));
	}
	
	public void addAccess(Access access) {
		this.access.add(access);
	}

	public Hostname getHostname() {
		return hostname;
	}

	public void setHostname(Hostname hostname) {
		this.hostname = hostname;
	}
}
